package com.es;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * 测试keyby reduce 增量聚合操作
 */
public class Test3 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.readTextFile("E:/test/haha.txt");

/*        dataStreamSource.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String s) throws Exception {
                String[] split = s.split("\t");
                return Tuple2.of(split[0], split[1]);
            }
        }).keyBy(0).reduce(new ReduceFunction<Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> reduce(Tuple2<String, String> stringStringTuple2, Tuple2<String, String> t1) throws Exception {
                System.out.println("执行操作：" + stringStringTuple2 + ", " + t1);
                return Tuple2.of(stringStringTuple2.f0, stringStringTuple2.f1 + t1.f1);
            }
        }).print();*/


        dataStreamSource.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String s) throws Exception {
                String[] split = s.split("\t");
                return Tuple2.of(split[0], split[1]);
            }
        }).keyBy(0).countWindow(1).apply(new WindowFunction<Tuple2<String, String>, Object, Tuple, GlobalWindow>() {

            @Override
            public void apply(Tuple tuple, GlobalWindow window, Iterable<Tuple2<String, String>> input, Collector<Object> out) throws Exception {
                Iterator<Tuple2<String, String>> iterator = input.iterator();
                while (iterator.hasNext()) {
                    Tuple2<String, String> next = iterator.next();
                    System.out.println("执行操作：" + next.f0 + ", " + next.f1);
                    out.collect( next.f1 + "======");
                }
            }
        }).print();

        env.execute("Test3");
    }
}
