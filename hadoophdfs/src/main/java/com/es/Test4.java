package com.es;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * keyBy timeWindow
 */
public class Test4 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.socketTextStream("swarm-manager", 9002);
        dataStreamSource.map(new MapFunction<String, Tuple2<String, String>>() {
            @Override
            public Tuple2<String, String> map(String s) throws Exception {
                String[] split = s.split("\t");
                return Tuple2.of(split[0], split[1]);
            }
        }).keyBy(0).timeWindow(Time.seconds(5)).apply(new WindowFunction<Tuple2<String, String>, Object, Tuple, TimeWindow>() {
            @Override
            public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<String, String>> input, Collector<Object> out) throws Exception {
                Iterator<Tuple2<String, String>> iterator = input.iterator();
                while (iterator.hasNext()) {
                    Tuple2<String, String> next = iterator.next();
                    System.out.println("执行操作：" + next.f0 + ", " + next.f1);
                    out.collect( next.f1 + "======");
                }
            }
        }).print();
        env.execute("test4");
    }
}
