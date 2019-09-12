package com.vincent.course04;

import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

public class JavaDataSetTransformationApp {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
//        mapFunction(executionEnvironment);
//        filterFunction(executionEnvironment);
//        mapPartitionFunction(executionEnvironment);
//        firstFunction(executionEnvironment);
//        flatMapFunction(executionEnvironment);
//        distinctFunction(executionEnvironment);
//        joinFunction(executionEnvironment);
//        outjoinFunction(executionEnvironment);
        crossFunction(executionEnvironment);
    }

    public static void crossFunction(ExecutionEnvironment env) throws Exception {
        List<String> info1 = new ArrayList<>();
        info1.add("乔峰");
        info1.add("慕容复");
        List<String> info2 = new ArrayList<>();
        info2.add("3");
        info2.add("1");
        info2.add("0");
        DataSource<String> data1 = env.fromCollection(info1);
        DataSource<String> data2 = env.fromCollection(info2);
        data1.cross(data2).print();
    }

    public static void outjoinFunction(ExecutionEnvironment env) throws Exception {
        List<Tuple2<Integer, String>> info1 = new ArrayList<>(); //编号 名字
        info1.add(new Tuple2<>(1, "hadoop"));
        info1.add(new Tuple2<>(2, "spark"));
        info1.add(new Tuple2<>(3, "flink"));
        info1.add(new Tuple2<>(4, "java"));

        List<Tuple2<Integer, String>> info2 = new ArrayList<>(); //编号 城市
        info2.add(new Tuple2<>(1, "北京"));
        info2.add(new Tuple2<>(2, "上海"));
        info2.add(new Tuple2<>(3, "深圳"));
        info2.add(new Tuple2<>(5, "广州"));
        DataSource<Tuple2<Integer, String>> data1 = env.fromCollection(info1);
        DataSource<Tuple2<Integer, String>> data2 = env.fromCollection(info2);
        data1.leftOuterJoin(data2).where(0).equalTo(0).with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {
            @Override
            public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                if (second == null) {
                    return new Tuple3<Integer, String, String>(first.f0, first.f1, "-");
                }
                return new Tuple3<Integer, String, String>(first.f0, first.f1, second.f1);
            }
        }).print();
        data1.rightOuterJoin(data2).where(0).equalTo(0).with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {
            @Override
            public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                if (first == null) {
                    return new Tuple3<Integer, String, String>(second.f0, "-", second.f1);
                }
                return new Tuple3<Integer, String, String>(first.f0, first.f1, second.f1);
            }
        }).print();
        data1.fullOuterJoin(data2).where(0).equalTo(0).with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {
            @Override
            public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                if (first == null) {
                    return new Tuple3<Integer, String, String>(second.f0, "-", second.f1);
                } else if (second == null) {
                    return new Tuple3<Integer, String, String>(first.f0, first.f1, "-");
                }
                return new Tuple3<Integer, String, String>(first.f0, first.f1, second.f1);
            }
        }).print();
    }

    public static void joinFunction(ExecutionEnvironment env) throws Exception {
        List<Tuple2<Integer, String>> info1 = new ArrayList<>(); //编号 名字
        info1.add(new Tuple2<>(1, "hadoop"));
        info1.add(new Tuple2<>(2, "spark"));
        info1.add(new Tuple2<>(3, "flink"));
        info1.add(new Tuple2<>(4, "java"));

        List<Tuple2<Integer, String>> info2 = new ArrayList<>(); //编号 城市
        info2.add(new Tuple2<>(1, "北京"));
        info2.add(new Tuple2<>(2, "上海"));
        info2.add(new Tuple2<>(3, "深圳"));
        info2.add(new Tuple2<>(5, "广州"));
        DataSource<Tuple2<Integer, String>> data1 = env.fromCollection(info1);
        DataSource<Tuple2<Integer, String>> data2 = env.fromCollection(info2);
        data1.join(data2).where(0).equalTo(0).with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>() {
            @Override
            public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second) throws Exception {
                return new Tuple3<Integer, String, String>(first.f0, first.f1, second.f1);
            }
        }).print();
    }

    public static void distinctFunction(ExecutionEnvironment env) throws Exception {
        List<String> info = new ArrayList<>();
        info.add("hadoop,spark");
        info.add("hadoop,flink");
        info.add("flink,flink");
        DataSource<String> data = env.fromCollection(info);
        data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String input, Collector<String> out) throws Exception {
                String[] splits = input.split(",");
                for (String split : splits) {
                    //发送出去
                    out.collect(split);
                }
            }
        }).distinct().print();
    }

    public static void flatMapFunction(ExecutionEnvironment env) throws Exception {
        List<String> info = new ArrayList<>();
        info.add("hadoop,spark");
        info.add("hadoop,flink");
        info.add("flink,flink");
        DataSource<String> data = env.fromCollection(info);
        data.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String input, Collector<String> out) throws Exception {
                String[] splits = input.split(",");
                for (String split : splits) {
                    //发送出去
                    out.collect(split);
                }
            }
        }).map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                return new Tuple2<>(value, 1);
            }
        }).groupBy(0).sum(1).print();
    }


    public static void firstFunction(ExecutionEnvironment env) throws Exception {
        List<Tuple2<Integer, String>> info = new ArrayList<>();
        info.add(new Tuple2<>(1, "hadoop"));
        info.add(new Tuple2<>(1, "spark"));
        info.add(new Tuple2<>(1, "flink"));
        info.add(new Tuple2<>(2, "java"));
        info.add(new Tuple2<>(2, "springboot"));
        info.add(new Tuple2<>(3, "linux"));
        info.add(new Tuple2<>(4, "vue"));
        DataSource<Tuple2<Integer, String>> data = env.fromCollection(info);
        data.first(3).print();
        data.groupBy(0).first(2).print();
        data.groupBy(0).sortGroup(1, Order.ASCENDING).first(2).print();
    }

    public static void mapPartitionFunction(ExecutionEnvironment env) throws Exception {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add("student:" + i);
        }
        DataSource<String> data = env.fromCollection(list);
        /*data.map(new MapFunction<String, String>() {
            @Override
            public String map(String input) throws Exception {
                String connection = DBUtils.getConnection();
                System.out.println("connection = [" + connection + "]");
                DBUtils.returnConnection(connection);
                return input;
            }
        }).print();*/
        data.mapPartition(new MapPartitionFunction<String, Object>() {
            @Override
            public void mapPartition(Iterable<String> values, Collector<Object> out) throws Exception {
                String connection = DBUtils.getConnection();
                System.out.println("connection = [" + connection + "]");
                DBUtils.returnConnection(connection);
            }
        }).print();
    }

    public static void filterFunction(ExecutionEnvironment env) throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        DataSource<Integer> data = env.fromCollection(list);
        data.map(new MapFunction<Integer, Integer>() {
            public Integer map(Integer input) {
                return input + 1;
            }
        }).filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer input) throws Exception {
                return input > 5;
            }
        }).print();
    }

    public static void mapFunction(ExecutionEnvironment executionEnvironment) throws Exception {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i + "");
        }
        DataSource<String> data = executionEnvironment.fromCollection(list);
        data.map(new MapFunction<String, Integer>() {
            public Integer map(String input) {
                return Integer.parseInt(input) + 1;
            }
        }).print();
    }
}
