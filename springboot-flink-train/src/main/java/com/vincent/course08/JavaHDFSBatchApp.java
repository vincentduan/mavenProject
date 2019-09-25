package com.vincent.course08;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

public class JavaHDFSBatchApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> dataSource = env.readTextFile("hdfs://swarm-manager:9000/LICENSE-2.0.txt");
        dataSource.print();
    }
}
