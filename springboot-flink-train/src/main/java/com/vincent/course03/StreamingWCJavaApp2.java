package com.vincent.course03;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * keyBy方式
 */
public class StreamingWCJavaApp2 {

    public static void main(String[] args) throws Exception {
        // step1: 获取流处理上下文环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // step2: 读取数据
        DataStreamSource<String> text = env.socketTextStream("192.168.152.45", 9999);
        // step3: transform

        text.flatMap(new FlatMapFunction<String, WC>() {
            @Override
            public void flatMap(String value, Collector<WC> out) throws Exception {
                String[] tokens = value.toLowerCase().split(",");
                for (String token : tokens) {
                    if (token.length() > 0) {
                        out.collect(new WC(token, 1));
                    }
                }
            }
        }).keyBy(new KeySelector<WC, Object>() {
            @Override
            public Object getKey(WC value) throws Exception {
                return value.word;
            }
        }).timeWindow(Time.seconds(5)).sum("count").print().setParallelism(1);
        env.execute("StreamingWCJavaApp");
    }

    public static class WC {
        private String word;
        private int count;

        public WC() {
        }

        public WC(String word, int count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
