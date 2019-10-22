package com.vincent;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class ParalTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        // executionEnvironment.setParallelism(1);

        DataStreamSource<String> dataStreamSource = executionEnvironment.socketTextStream("swarm-manager", 9002);
        // DataStreamSource<String> dataStreamSource = executionEnvironment.readTextFile("E:/test/paral.txt");
        dataStreamSource.broadcast().map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String s) throws Exception {
                String[] split = s.split(",");
                String name = split[0];
                String age = split[1];
                return Tuple2.of(name, age);
            }
        }).print();
        executionEnvironment.execute("KafkaTest");
    }
}
