package com.vincent;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.*;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        String propertiesPath = args[0];
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(propertiesPath);
        List<HttpHost> esAddresses = ElasticSearchSinkUtil.getEsAddresses(parameterTool.get("es.hosts"));
        int bulk_size = parameterTool.getInt("es.bulk.flushMaxAction");
        int sinkParallelism = parameterTool.getInt("es.sink.parallelism");
        String rawPath = parameterTool.get("rawPath");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.readTextFile(rawPath);

        SingleOutputStreamOperator<Tuple7<String, String, String, String, String, String, String>> map = dataStreamSource.map(new MapFunction<String, Tuple7<String, String, String, String, String, String, String>>() {
            @Override
            public Tuple7<String, String, String, String, String, String, String> map(String s) throws Exception {
                String[] splits = s.split("\t");
                String uid = splits[0];
                String timestamp = splits[1];
                String desc_info = splits[2];
                String related_identity = splits[3];
                String record_num = splits[4];
                String desc_type = splits[5];
                String date = splits[6];
                return new Tuple7<>(uid, timestamp, desc_info, related_identity, record_num, desc_type, date);
            }
        });

        ElasticSearchSinkUtil.addSink(esAddresses, bulk_size, sinkParallelism, map, new ElasticsearchSinkFunction<Tuple7<String, String, String, String, String, String, String>>() {
            @Override
            public void process(Tuple7<String, String, String, String, String, String, String> data, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {

                IndexRequest indexRequest = null;
                try {
                    indexRequest = createIndexRequest(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                requestIndexer.add(indexRequest);
            }



            public IndexRequest createIndexRequest(Tuple7<String, String, String, String, String, String, String> data) throws IOException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", data.f0);
                jsonObject.put("timestamp", data.f1);
                jsonObject.put("desc_info", JSONObject.parseObject(data.f2));
                jsonObject.put("related_identity", JSONObject.parseObject(data.f3));
                jsonObject.put("record_num", data.f4);
                jsonObject.put("desc_type", data.f5);
                jsonObject.put("date", data.f6);
                return Requests.indexRequest()
                        .index("internet_user_daily_hk-20190701-20190731-2")
                        .type("type").source(jsonObject.toString(), XContentType.JSON);
            }
        });

        // map.setParallelism(1).print();
        env.execute("Test");
    }
}
