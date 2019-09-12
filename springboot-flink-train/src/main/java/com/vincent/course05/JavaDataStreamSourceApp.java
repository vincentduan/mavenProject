package com.vincent.course05;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class JavaDataStreamSourceApp {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
//        socketFunction(environment);
//        nonParallelSourceFunction(environment);
//        parallelSourceFunction(environment);
        richpParallelSourceFunction(environment);
        environment.execute("JavaDataStreamSourceApp");
    }

    public static void richpParallelSourceFunction(StreamExecutionEnvironment executionEnvironment){
        DataStreamSource data = executionEnvironment.addSource(new JavaCustomRichParallelSourceFunction()).setParallelism(2);
        data.print().setParallelism(1);
    }

    public static void parallelSourceFunction(StreamExecutionEnvironment executionEnvironment){
        DataStreamSource data = executionEnvironment.addSource(new JavaCustomParallelSourceFunction()).setParallelism(2);
        data.print().setParallelism(1);
    }

    public static void nonParallelSourceFunction(StreamExecutionEnvironment executionEnvironment){
        DataStreamSource data = executionEnvironment.addSource(new JavaCustomNonParallelSourceFunction()).setParallelism(2);
        data.print().setParallelism(1);
    }

    public static void socketFunction(StreamExecutionEnvironment executionEnvironment){
        DataStreamSource<String> data = executionEnvironment.socketTextStream("192.168.152.45", 9999);
        data.print().setParallelism(1);
    }
}
