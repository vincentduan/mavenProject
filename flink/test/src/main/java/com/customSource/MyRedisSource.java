package com.customSource;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.Map;

/**
 * redis中进行数据初始化
 * hset areas AREA_US US
 * hset areas AREA_CT TW,HK
 * hset areas AREA_AR PK,KW,SA
 * hset areas AREA_IN IN
 * <p>
 * 需要把大区和国家的对应关系组装成java的hashmap
 * <p>
 * 在redis中保存的有国家和大区的关系
 */
public class MyRedisSource extends RichSourceFunction<HashMap<String, String>> {
    Logger logger = LoggerFactory.getLogger(MyRedisSource.class);

    private final long SLEEP_MILLION = 10000;
    private boolean isRunning = true;
    private Jedis jedis = null;

    @Override
    public void run(SourceContext<HashMap<String, String>> ctx) throws Exception {
        this.jedis = new Jedis("swarm-manager", 6379);
        // 存储所有国家和大区的对应关系
        HashMap<String, String> result = new HashMap<>();
        while (isRunning) {
            try {
                result.clear();
                Map<String, String> areas = jedis.hgetAll("areas");
                for (Map.Entry<String, String> entry : areas.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String[] splits = value.split(",");
                    for (String split : splits) {
                        result.put(split, key);
                    }
                }
                if (result.size() > 0) {
                    ctx.collect(result);
                } else {
                    logger.warn("从redis中获取的数据为空！！！");
                }
            } catch (JedisConnectionException e) {
                logger.error("redis连接异常，重新获取连接", e.getCause());
                jedis = new Jedis("swarm-manager", 6379);
            } catch (Exception e) {
                logger.error("异常", e.getCause());
            }


            Thread.sleep(SLEEP_MILLION);

        }
    }

    @Override
    public void cancel() {
        isRunning = false;
        if (jedis != null) {
            jedis.close();
        }
    }
}
