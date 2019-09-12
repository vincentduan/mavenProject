package com.vincent.course04;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaDistributedCachedApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        executionEnvironment.registerCachedFile("E:/test/hello.txt", "pk-java-dc");
        DataSource<String> data1 = executionEnvironment.fromElements("hadoop", "spark", "flink", "pyspark");
        data1.map(new RichMapFunction<String, String>() {
            List<String> list = new ArrayList<>();

            @Override
            public void open(Configuration parameters) throws Exception {
                File file = getRuntimeContext().getDistributedCache().getFile("pk-java-dc");
                List<String> lines = FileUtils.readLines(file);
                for (String line : lines) {
                    list.add(line);
                    System.out.println(list);
                }
            }

            @Override
            public String map(String value) throws Exception {
                return value;
            }
        }).print();

    }
}
