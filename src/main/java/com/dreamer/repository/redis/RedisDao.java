package com.dreamer.repository.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by huangfei on 21/06/2017.
 */
public class RedisDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

//    public RedisDao(String ip, int port) {
//        jedisPool = new JedisPool(ip,port);
//    }


    public RedisDao(String ip, int port, JedisPoolConfig jedisPoolConfig) {
        jedisPool = new JedisPool(jedisPoolConfig,ip,port);
    }

    public String getStr(String key) {
        //redis操作的逻辑
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] bytes = jedis.get(key.getBytes());
            //获取到了
            return new String(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            jedis.close();
        }
        return null;
    }

    public String putStr(String key, String value) {
        //set Object(seckill) -> 序列化 -> byte[]
        Jedis jedis = jedisPool.getResource();
        try {
            int timeout = 6500;//1小时
            String result = jedis.setex(key.getBytes(), timeout, value.getBytes());
            return result;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            jedis.close();
        }
        return null;
        }




}
