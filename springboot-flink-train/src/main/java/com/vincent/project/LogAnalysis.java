package com.vincent.project;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.elasticsearch.ActionRequestFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.http.HttpHost;
import org.apache.kafka.common.serialization.StringSerializer;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class LogAnalysis {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        String topic = "mytest";
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.170.170:9092");
        properties.setProperty("group.id", "test");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        FlinkKafkaConsumer<String> flinkKafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);

        //接收kafka
        DataStreamSource<String> data = executionEnvironment.addSource(flinkKafkaConsumer);


        executionEnvironment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        SingleOutputStreamOperator<Tuple3<Long, String, String>> logData = data.map(new MapFunction<String, Tuple4<String, Long, String, String>>() {
            @Override
            public Tuple4<String, Long, String, String> map(String s) throws Exception {
                String[] splits = s.split("\t");
                String level = splits[2];
                String timeStr = splits[3];
                long time = 0l;
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time = simpleDateFormat.parse(timeStr).getTime();
                } catch (Exception e) {
                    log.error("time parse error: {}", e.getMessage());
                }

                String domain = splits[5];
                String traffic = splits[6];
                return new Tuple4<>(level, time, domain, traffic);
            }
        }).filter((FilterFunction<Tuple4<String, Long, String, String>>) stringLongStringStringTuple4 -> {
            // 在生产上进行业务处理的时候，一定要考虑处理的健壮性以及数据的准确性，
            // 脏数据或者不是符合业务规则的数据是需要全部过滤掉之后
            // 再进行相应业务逻辑的处理
            // 对于我们的业务来说，我们只需要统计level=E的即可
            // 对于level非E的，不作为我们业务指标的统计范畴
            // 数据清洗：就是按照我们的业务规则把原始输入的数据，进行一定业务规则的处理，使得满足我们的也无需求为准
            return stringLongStringStringTuple4.f1 != 0;
        }).filter((FilterFunction<Tuple4<String, Long, String, String>>) (Tuple4<String, Long, String, String> stringLongStringStringTuple4) -> stringLongStringStringTuple4.f0.equals("E"))
                .map(new MapFunction<Tuple4<String, Long, String, String>, Tuple3<Long, String, String>>() {
                    @Override
                    public Tuple3<Long, String, String> map(Tuple4<String, Long, String, String> stringLongStringStringTuple4) throws Exception {
                        return new Tuple3<>(stringLongStringStringTuple4.f1, stringLongStringStringTuple4.f2, stringLongStringStringTuple4.f3);
                    }
                });

        SingleOutputStreamOperator<Tuple3<String, String, String>> apply = logData.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Tuple3<Long, String, String>>() {

            private final long maxOutOfOrderness = 10000L; // 10 seconds
            private long currentMaxTimestamp;

            @Nullable
            @Override
            public Watermark getCurrentWatermark() {
                return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
            }

            @Override
            public long extractTimestamp(Tuple3<Long, String, String> longStringStringTuple3, long l) {
                Long timestamp = longStringStringTuple3.f0;
                currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
                return timestamp;
            }
        }).keyBy(1)//此处是按照域名进行keyBy的
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .apply(new WindowFunction<Tuple3<Long, String, String>, Tuple3<String, String, String>, Tuple, TimeWindow>() {

                    /**
                     *
                     * @param tuple
                     * @param timeWindow
                     * @param iterable
                     * @param collector
                     * @throws Exception
                     */
                    @Override
                    public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<Tuple3<Long, String, String>> iterable, Collector<Tuple3<String, String, String>> collector) throws Exception {
                        String domain = tuple.getField(0).toString();
                        long sum = 0l;
                        Iterator<Tuple3<Long, String, String>> iterator = iterable.iterator();
                        String timeStr = "";
                        while (iterator.hasNext()) {
                            Tuple3<Long, String, String> next = iterator.next();
                            sum += Long.parseLong(next.f2);
                            Long timeLong = next.f0;
                            timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timeLong));
                        }
                        /**
                         * 输出第一个参数是时间，
                         * 第二个参数是域名，
                         * 第三个参数是流量的和
                         */
                        collector.collect(new Tuple3<String, String, String>(timeStr, domain, sum + ""));
                    }
                });
        // apply.print().setParallelism(1);

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("swarm-manager", 9200, "http"));
        httpHosts.add(new HttpHost("swarm-worker2", 9200, "http"));

        ElasticsearchSink.Builder<Tuple3<String, String, String>> esSinkBuilder = new ElasticsearchSink.Builder<>(httpHosts, new ElasticsearchSinkFunction<Tuple3<String, String, String>>() {
            public IndexRequest createIndexRequest(Tuple3<String, String, String> element) {
                Map<String, String> json = new HashMap<>();
                json.put("time", element.f0);
                json.put("domain", element.f1);
                json.put("traffic", element.f2);
                String id = element.f0 + "-" + element.f1;
                return Requests.indexRequest()
                        .index("ddy_index")
                        .type("type")
                        .source(json);
            }

            @Override
            public void process(Tuple3<String, String, String> element, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
                IndexRequest indexRequest = createIndexRequest(element);
                requestIndexer.add(indexRequest);
            }
        });
        esSinkBuilder.setFailureHandler(new ActionRequestFailureHandler() {
            @Override
            public void onFailure(ActionRequest actionRequest, Throwable throwable, int i, RequestIndexer requestIndexer) throws Throwable {
                System.out.println("failure");
                System.out.println(throwable.getMessage());
            }
        });
        esSinkBuilder.setBulkFlushInterval(1000);
        apply.addSink(esSinkBuilder.build());
        executionEnvironment.execute("LogAnalysis");
    }
}
