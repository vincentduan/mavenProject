package com.vincent.project;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.HashMap;

public class MySqlSourceTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<HashMap<String, String>> data = executionEnvironment.addSource(new MySqlSource());
        data.setParallelism(1).print();
        executionEnvironment.execute("MySqlSourceTest");
    }
}
