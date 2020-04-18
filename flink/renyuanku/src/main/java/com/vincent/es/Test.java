package com.vincent.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws Exception {
        String propertiesPath = args[0];
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(propertiesPath);
        List<HttpHost> esAddresses = ElasticSearchSinkUtil.getEsAddresses(parameterTool.get("es.hosts"));
        int bulk_size = parameterTool.getInt("es.bulk.flushMaxAction");
        int sinkParallelism = parameterTool.getInt("es.sink.parallelism");
        String rawPath = parameterTool.get("rawPath");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.readTextFile(rawPath).setParallelism(1);
        SingleOutputStreamOperator<Tuple5<String, String, String, String, String>> map = dataStreamSource.map(new MapFunction<String, Tuple5<String, String, String, String, String>>() {
            @Override
            public Tuple5<String, String, String, String, String> map(String s) throws Exception {
                String[] splits = s.split("\t");
                String uid = splits[0];
                String related_identity = splits[3];
                JSONObject jsonObject = JSONObject.parseObject(related_identity);
                String imei = "";
                String imsi = "";
                String mac = "";
                String msisdn = "";
                if (jsonObject.containsKey("imei")) {
                    imei = jsonObject.getString("imei");
                }
                if (jsonObject.containsKey("imsi")) {
                    imsi = jsonObject.getString("imsi");
                }
                if (jsonObject.containsKey("mac")) {
                    mac = jsonObject.getString("mac");
                }
                if (jsonObject.containsKey("msisdn")) {
                    msisdn = jsonObject.getString("msisdn");
                }
                return new Tuple5<>(uid, imei, imsi, mac, msisdn);
            }
        });

        SingleOutputStreamOperator<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> renyuanku = AsyncDataStream.orderedWait(map, new AsyncEsDataRequest(), 20, TimeUnit.SECONDS, 100);

        SingleOutputStreamOperator<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> temp = renyuanku.keyBy(0).reduce(new ReduceFunction<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>>() {
            @Override
            public Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> reduce(Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> stringSetSetSetSetTuple5, Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> t1) throws Exception {
                stringSetSetSetSetTuple5.f1.addAll(t1.f1);
                stringSetSetSetSetTuple5.f2.addAll(t1.f2);
                stringSetSetSetSetTuple5.f3.addAll(t1.f3);
                stringSetSetSetSetTuple5.f4.addAll(t1.f4);
                return Tuple5.of(stringSetSetSetSetTuple5.f0, stringSetSetSetSetTuple5.f1, stringSetSetSetSetTuple5.f2, stringSetSetSetSetTuple5.f3, stringSetSetSetSetTuple5.f4);
            }
        });

        ElasticSearchSinkUtil.addSink(esAddresses, bulk_size, sinkParallelism, temp, new ElasticsearchSinkFunction<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>>() {
            @Override
            public void process(Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> data, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {

                IndexRequest indexRequest = null;
                try {
                    indexRequest = createIndexRequest(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                requestIndexer.add(indexRequest);
            }



            public IndexRequest createIndexRequest(Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> data) throws IOException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", data.f0);
                jsonObject.put("imei", data.f1);
                jsonObject.put("imsi", data.f2);
                jsonObject.put("mac", data.f3);
                jsonObject.put("msisdn", data.f4);
                return Requests.indexRequest()
                        .index("renyuanku")
                        .type("type").id(data.f0).source(jsonObject.toString(), XContentType.JSON);
            }
        });
/*        renyuanku.map(new MapFunction<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>, String>() {
            @Override
            public String map(Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> stringSetSetSetSetTuple5) throws Exception {
                return stringSetSetSetSetTuple5.f0;
            }
        }).writeAsText("E:/test/renyuanku.txt").setParallelism(1);*/
        // renyuanku.writeAsText("E:/test/renyuanku.txt").setParallelism(1);

        // map.setParallelism(1).print();
        env.execute("Test");
    }
}
