package com.vincent;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Set;


/**
 * @author vincent
 * @time 2019-10-03 12:43
 */
public class RedisTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStreamSource<String> dataStreamSource = env.readTextFile("file:///Users/duandingyang/test-data/test.txt");
//        SingleOutputStreamOperator<Tuple3<String, String, String>> map = dataStreamSource.map(new MapFunction<String, Tuple3<String, String, String>>() {
//            @Override
//            public Tuple3<String, String, String> map(String value) throws Exception {
//                String[] split = value.split("\t");
//                return new Tuple3<>(split[0], split[1], split[2]);
//            }
//        });
//        map.addSink(new RedisSink()).setParallelism(1);
        DataStreamSource<Tuple2<String, Set<String>>> redisSource = env.addSource(new RedisSource());
        DataStreamSource<Tuple2<String, Set<String>>> esSource = env.addSource(new ElasticsearchSource());
        env.execute("RedisTest");
    }
}
