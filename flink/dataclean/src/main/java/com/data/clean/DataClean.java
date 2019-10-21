package com.data.clean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.clean.source.MyRedisSource;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Properties;

public class DataClean {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 指定kafkasource
        String topic = "allData";
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "swarm-manager:9092");
        properties.setProperty("group.id", "con1");
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);

        //获取kafka中的数据
        //{"dt": "2019-10-21: 11:22:00", "countryCode":"US", "data": [{"type": "s1", "score": 0.3, "level": "B"}, {"type": "s2", "score": 0.1, "level": "A"}]}
        DataStreamSource<String> data = env.addSource(kafkaConsumer);
        System.out.println("data paral:" + data.getParallelism());
        // 最新国家码和大区的映射关系
        DataStream<HashMap<String, String>> mapData = env.addSource(new MyRedisSource()).broadcast(); //可以把数据发送到后面算子的所有并行实例中
        System.out.println("mapData paral:" + mapData.getParallelism());
        // CoFlatMapFunction第一个类型表示data传入的类型, 第二个类型表示mapData传入的类型, 第三个类型表示输出的类型
        SingleOutputStreamOperator<String> resData = data.connect(mapData).flatMap(new CoFlatMapFunction<String, HashMap<String, String>, String>() {

            //存储国家和大区的映射关系
            private HashMap<String, String> allMap = new HashMap<>();

            // flatmap1 处理的是kafka中的数据
            @Override
            public void flatMap1(String value, Collector<String> out) throws Exception {
                JSONObject jsonObject = JSONObject.parseObject(value);
                String dt = jsonObject.getString("dt");
                String countryCode = jsonObject.getString("countryCode");
                // 获取大区
                String area = allMap.get(countryCode);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    System.out.println("Thread id:" + Thread.currentThread().getId() + ",area:---------" + area + "," + dt);
                    jsonObject1.put("area", area);
                    jsonObject1.put("dt", dt);
                    out.collect(jsonObject1.toJSONString());
                }
            }

            // flatmap2 处理的是redis返回的map数据
            @Override
            public void flatMap2(HashMap<String, String> value, Collector<String> out) throws Exception {
                this.allMap = value;
            }
        });
        System.out.println("resData paral:" + resData.getParallelism());
        String outTopic = "allDataClean";
        Properties outProperties = new Properties();
        outProperties.setProperty("bootstrap.servers", "swarm-manager:9092");
        FlinkKafkaProducer<String> myProducer = new FlinkKafkaProducer<>(outTopic, new KeyedSerializationSchemaWrapper<>(new SimpleStringSchema()), outProperties, FlinkKafkaProducer.Semantic.EXACTLY_ONCE);
        resData.addSink(myProducer);


        env.execute("DataClean");
    }
}
