package com.vincent;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author vincent
 * @time 2019-10-05 14:56
 */
public class JoinFunctionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source1 = env.socketTextStream("localhost", 9001);
        DataStreamSource<String> source2 = env.socketTextStream("localhost", 9002);
        DataStream<String> apply = source1.join(source2).where(new KeySelector<String, String>() {
            @Override
            public String getKey(String value) throws Exception {
                return value.split(" ")[0];
            }
        }).equalTo(new KeySelector<String, String>() {
            @Override
            public String getKey(String value) throws Exception {
                return value.split(" ")[0];
            }
        }).window(ProcessingTimeSessionWindows.withGap(Time.seconds(30))).trigger(CountTrigger.of(1))
                .apply(new JoinFunction<String, String, String>() {
                    @Override
                    public String join(String first, String second) throws Exception {
                        return first.split(" ")[1] + "=>" + second.split(" ")[1];
                    }
                });
        apply.print();
        env.execute("JoinFunctionDemo");


    }
}
