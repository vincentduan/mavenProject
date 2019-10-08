package com.vincent;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import redis.clients.jedis.Jedis;

/**
 * @author vincent
 * @time 2019-10-03 12:18
 */
public class RedisSink extends RichSinkFunction<Tuple3<String, String, String>> {
    Jedis jedis = null;
    @Override
    public void open(Configuration parameters) throws Exception {
        jedis = new Jedis("localhost");
        String name = jedis.get("name");
        System.out.println(name);
    }

    @Override
    public void close() throws Exception {
        System.out.println("close");
        super.close();
    }

    @Override
    public void invoke(Tuple3<String, String, String> value, Context context) throws Exception {
        jedis.set(value.f0, value.f1);
        jedis.set(value.f0, value.f2);
        System.out.println(value.f0+ "," + value.f1 + "," + value.f2);
    }
}
