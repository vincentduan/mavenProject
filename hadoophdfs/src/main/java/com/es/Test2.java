package com.es;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.HashSet;
import java.util.Set;

public class Test2 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.readTextFile("E:/test/uid_person.txt");
        SingleOutputStreamOperator<Tuple2<String, Set<String>>> map = dataStreamSource.map(new MapFunction<String, Tuple2<String, Set<String>>>() {
            @Override
            public Tuple2<String, Set<String>> map(String s) throws Exception {
                String[] split = s.split("\t");
                String uid = split[0];
                String name = split[1];
                Set<String> set = new HashSet();
                set.add(name);
                return Tuple2.of(uid, set);
            }
        });
        map.keyBy(0).reduce(new ReduceFunction<Tuple2<String, Set<String>>>() {
            @Override
            public Tuple2<String, Set<String>> reduce(Tuple2<String, Set<String>> stringSetTuple2, Tuple2<String, Set<String>> t1) throws Exception {
                stringSetTuple2.f1.addAll(t1.f1);
                return Tuple2.of(stringSetTuple2.f0, stringSetTuple2.f1);
            }
        }).writeAsText("E:/test/mytest.txt").setParallelism(1);
        env.execute("Test");
    }
}
