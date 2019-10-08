package com.vincent;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.Set;

/**
 * @author vincent
 * @time 2019-10-04 21:07
 */
public class ElasticsearchSource extends RichSourceFunction<Tuple2<String, Set<String>>> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void run(SourceContext<Tuple2<String, Set<String>>> ctx) throws Exception {

    }

    @Override
    public void cancel() {

    }
}
