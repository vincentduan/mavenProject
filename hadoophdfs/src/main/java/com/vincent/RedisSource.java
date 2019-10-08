package com.vincent;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author vincent
 * @time 2019-10-04 00:51
 */
public class RedisSource extends RichSourceFunction<Tuple2<String, Set<String>>> {
    Jedis jedis = null;
    JedisPoolConfig jedisPoolConfig = null;
    //获取连接池
    JedisPool jedisPool =null;
    @Override
    public void open(Configuration parameters) throws Exception {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPool = new JedisPool(jedisPoolConfig,"localhost",6379);
        jedis = jedisPool.getResource();
    }

    @Override
    public void run(SourceContext<Tuple2<String, Set<String>>> ctx) throws Exception {
        Set<String> keys = jedis.keys("*");
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()){
            String key = iterator.next();
            String type = jedis.type(key);
            if("set".equals(type)){
                System.out.println(key);
                Set<String> temp_values = jedis.smembers(key);
                Set<String> set_test = new HashSet<>(temp_values);
                ctx.collect(new Tuple2<>(key, set_test));
            }
        }
        jedis.flushAll();
    }

    @Override
    public void cancel() {

    }
}
