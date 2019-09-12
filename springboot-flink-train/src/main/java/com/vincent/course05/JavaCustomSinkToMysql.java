package com.vincent.course05;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class JavaCustomSinkToMysql {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = environment.socketTextStream("192.168.152.45", 9999);
        SingleOutputStreamOperator<Student> studentStream = source.map(new MapFunction<String, Student>() {
            @Override
            public Student map(String value) throws Exception {
                String[] splits = value.split(",");
                Student student = new Student();
                student.setId(Integer.parseInt(splits[0]));
                student.setName(splits[1]);
                student.setAge(Integer.parseInt(splits[2]));
                return student;
            }
        });
        studentStream.addSink(new SinkToMySQL());
        environment.execute("JavaCustomSinkToMysql");
    }
}
