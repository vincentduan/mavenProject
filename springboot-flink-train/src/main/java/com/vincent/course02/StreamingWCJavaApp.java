package com.vincent.course02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * 使用Java API来开发Flink的实时处理应用程序
 * wc统计的数据源自socket
 */
public class StreamingWCJavaApp {

    public static void main(String[] args) throws Exception {
        // step1: 获取流处理上下文环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // step2: 读取数据
        DataStreamSource<String> text = env.socketTextStream("192.168.152.45", 9999);
        // step3: transform
        text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                String[] tokens = value.toLowerCase().split(",");
                for(String token: tokens) {
                    if(token.length() > 0) {
                        out.collect(new Tuple2<String, Integer>(token, 1));
                    }
                }
            }
        }).keyBy(0).timeWindow(Time.seconds(5)).sum(1).print().setParallelism(1);
        env.execute("StreamingWCJavaApp");
    }

}
