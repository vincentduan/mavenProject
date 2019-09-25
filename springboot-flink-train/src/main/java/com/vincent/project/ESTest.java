package com.vincent.project;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESTest {
    public static void main(String[] args) {
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        List<MyData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyData myData = new MyData();
            myData.domain = "domain" + i;
            myData.time = "time" + i;
            myData.traffic = "traffic" + i;
            list.add(myData);
        }
        DataSource<MyData> dataSource = environment.fromCollection(list);
        MapOperator<MyData, Tuple3<String, String, String>> map = dataSource.map(new MapFunction<MyData, Tuple3<String, String, String>>() {

            @Override
            public Tuple3<String, String, String> map(MyData myData) throws Exception {
                return new Tuple3<>(myData.time, myData.domain, myData.traffic);
            }
        });

    }


    private static class MyData {
        public String time;
        public String domain;
        public String traffic;
    }
}
