package com.vincent.course04;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaDataSetSourceApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        // fromCollection(executionEnvironment);
//        textFile(executionEnvironment);
        csvFile(executionEnvironment);
    }

    public static void csvFile(ExecutionEnvironment env) throws Exception {
        String filePath = "E:/test/input/people.csv";
        DataSource<Tuple2<String, Integer>> types = env.readCsvFile(filePath).ignoreFirstLine().includeFields("11").types(String.class, Integer.class);
        types.print();
        env.readCsvFile(filePath).ignoreFirstLine().pojoType(People.class, "name", "age", "job").print();
    }

    public static void textFile(ExecutionEnvironment env) throws Exception {
        String filePath = "E:/test/input/hello.txt";
        // String filePath = "E:/test/input";
        env.readTextFile(filePath).print();
    }

    public static void fromCollection(ExecutionEnvironment env) throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        env.fromCollection(list).print();
    }
}
