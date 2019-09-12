package com.vincent.course04;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.core.fs.FileSystem;

import java.util.ArrayList;
import java.util.List;

public class JavaDataSetSinkApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        List<Integer> info = new ArrayList<>();
        for(int i = 1;i <=10; i++) {
            info.add(i);
        }
        DataSource<Integer> data1 = executionEnvironment.fromCollection(info);
        String filePath = "E:/test2";
        data1.writeAsText(filePath, FileSystem.WriteMode.OVERWRITE);
        executionEnvironment.execute("JavaDataSetSinkApp");
    }
}
