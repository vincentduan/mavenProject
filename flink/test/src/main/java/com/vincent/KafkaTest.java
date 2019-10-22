package com.vincent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.customSource.MyRedisSource;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Properties;

public class KafkaTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String topic = "allData";
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "swarm-manager:9092");
        properties.setProperty("group.id", "con1");
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);
        DataStreamSource<String> data = env.addSource(kafkaConsumer);
        DataStream<HashMap<String, String>> mapData = env.addSource(new MyRedisSource()).broadcast();
        data.connect(mapData).flatMap(new CoFlatMapFunction<String, HashMap<String, String>, String>() {
            private HashMap<String, String> allMap = new HashMap<>();
            @Override
            public void flatMap1(String value, Collector<String> out) throws Exception {
                JSONObject jsonObject = JSONObject.parseObject(value);
                String countryCode = jsonObject.getString("countryCode");
                // 获取大区
                String area = allMap.get(countryCode);
                jsonObject.put("countryCode", area);
                out.collect(jsonObject.toJSONString());
            }

            @Override
            public void flatMap2(HashMap<String, String> value, Collector<String> out) throws Exception {
                this.allMap = value;
            }
        }).print();
        env.execute("KafkaTest");
    }
}
