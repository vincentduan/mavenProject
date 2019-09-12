package com.vincent.course05;

//import org.apache.flink.streaming.api.functions.source.SourceFunction;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class JavaCustomNonParallelSourceFunction implements SourceFunction<Long> {

    boolean isRunning = true;
    long count = 1;

    @Override
    public void run(SourceFunction.SourceContext ctx) throws Exception {
        while (isRunning) {
            ctx.collect(count);
            count+=1;
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        isRunning=false;
    }
}
