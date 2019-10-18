package com.es;

import com.alibaba.fastjson.JSONObject;
import com.vincent.ElasticSearchSinkUtil;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.Collector;
import org.apache.http.HttpHost;

import java.util.Collection;
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
        DataStreamSource<String> dataStreamSource = env.readTextFile(rawPath);
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
/*      // 原始数据uid=316523,3803
        map.map(new MapFunction<Tuple5<String, String, String, String, String>, String>() {
            @Override
            public String map(Tuple5<String, String, String, String, String> stringStringStringStringStringTuple5) throws Exception {
                return stringStringStringStringStringTuple5.f0;
            }
        }).writeAsText("E:/test/map.txt").setParallelism(1);*/
        SingleOutputStreamOperator<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> renyuanku = AsyncDataStream.unorderedWait(map, new AsyncEsDataRequest(), 2, TimeUnit.SECONDS, 100);

        renyuanku.keyBy(0).reduce(new ReduceFunction<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>>() {
            @Override
            public Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> reduce(Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> stringSetSetSetSetTuple5, Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>> t1) throws Exception {
                stringSetSetSetSetTuple5.f1.addAll(t1.f1);
                stringSetSetSetSetTuple5.f2.addAll(t1.f2);
                stringSetSetSetSetTuple5.f3.addAll(t1.f3);
                stringSetSetSetSetTuple5.f4.addAll(t1.f4);
                return Tuple5.of(stringSetSetSetSetTuple5.f0, stringSetSetSetSetTuple5.f1, stringSetSetSetSetTuple5.f2, stringSetSetSetSetTuple5.f3, stringSetSetSetSetTuple5.f4);
            }
        }).writeAsText("E:/test/renyuanku.txt").setParallelism(1);
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
