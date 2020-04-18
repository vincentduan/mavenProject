package com.vincent.course04;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;

public class JavaCounterApp {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> data = executionEnvironment.fromElements("hadoop", "spark", "pyspark", "storm");
        DataSet dataSet = data.map(new RichMapFunction<String, String>() {
            LongCounter counter = new LongCounter();

            @Override
            public void open(Configuration parameters) throws Exception {
                getRuntimeContext().addAccumulator("ele-counts-java",counter);
                System.out.println("open counter: "+counter.getLocalValue());
            }

            @Override
            public String map(String value) throws Exception {
                counter.add(1);
                System.out.println("map counter: "+counter.getLocalValue());
                return value;
            }
        }).setParallelism(2);
        dataSet.writeAsText("E:/test4", FileSystem.WriteMode.OVERWRITE).setParallelism(3);
        JobExecutionResult javaCounterApp = executionEnvironment.execute("JavaCounterApp");
        long num = javaCounterApp.getAccumulatorResult("ele-counts-java");
        System.out.println("num:" + num);
    }
}
