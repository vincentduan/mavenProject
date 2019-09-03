package com.vincent.course04;

import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

public class JavaDataSetSourceApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        fromCollection(executionEnvironment);
    }
    public static void fromCollection(ExecutionEnvironment env) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i <= 10; i++) {
            list.add(i);
        }
        env.fromCollection(list).print();
    }
}
