package com.vincent;

import com.customSource.MyNoParalleSource;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class NoPralalleSourceBroadcast {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Long> dataStreamSource = env.addSource(new MyNoParalleSource()).setParallelism(1);//注意：针对此source，并行度只能设置为1;
        DataStream<Long> sum = dataStreamSource.broadcast().map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                long id = Thread.currentThread().getId();
                System.out.println("线程id："+id+",接收到数据：" + value);
                return value;
            }
        });
        sum.print().setParallelism(1);
        env.execute("NoPralalleSourceBroadcast");
    }
}
