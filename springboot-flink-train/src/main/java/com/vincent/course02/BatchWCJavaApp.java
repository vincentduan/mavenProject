package com.vincent.course02;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 使用Java API来开发Flink的批处理应用程序
 */
public class BatchWCJavaApp {
    public static void main(String[] args) throws Exception {
        String input = "E:/test/input/test.txt";
        // step1: 获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // step2: 读取数据
        DataSource<String> text = env.readTextFile(input);
        // step3: transform
        // FlatMapFunction<String, Tuple2<String, Integer>表示进来一个String, 转换成一个<String, Integer>类型
        text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            /**
             *
             * @param value 就是一行一行的字符串
             * @param out 转换成(单词,次数)
             * @throws Exception
             */
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                String[] tokens = value.toLowerCase().split("\t");
                for(String token: tokens) {
                    if(token.length() > 0) {
                        out.collect(new Tuple2<String, Integer>(token, 1));
                    }
                }
            }
        }).groupBy(0).sum(1).print();
    }
}
