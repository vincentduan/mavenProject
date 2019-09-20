package com.vincent.course08;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

public class JavaKafkaProducerApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> data = env.socketTextStream("192.168.227.128", 9999);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.227.128:9092");
        properties.setProperty("group.id", "test");
        data.addSink(new FlinkKafkaProducer<String>("192.168.227.128:9092", "mytest", new SimpleStringSchema()));
        env.execute("JavaKafkaProducerApp");
    }
}
