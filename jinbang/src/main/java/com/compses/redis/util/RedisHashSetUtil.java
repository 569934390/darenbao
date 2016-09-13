package com.compses.redis.util;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by jocelynsuebb on 16/8/2.
 */
public class RedisHashSetUtil {

    public static void hset(String key,String field ,String value){
        Jedis jedis = null;
        try {
            jedis = RedisFactoryUtil.getUserServiceRedis();
            jedis.hset(key,field,value);
        } catch (Exception e) {
            RedisFactoryUtil.returnBrokenJedis(jedis);
        } finally {
            RedisFactoryUtil.returnJedis(jedis);
        }
    }

    public static List<String> hvals(String key){
        List<String> list = null;
        Jedis jedis = null;
        try {
            jedis = RedisFactoryUtil.getUserServiceRedis();
            list = jedis.hvals(key);
        } catch (Exception e) {
            RedisFactoryUtil.returnBrokenJedis(jedis);
        } finally {
            RedisFactoryUtil.returnJedis(jedis);
        }
        return list;
    }

    public static List<String> hmget(String key, String...field){
        List<String> list = null;
        Jedis jedis = null;
        try {
            jedis = RedisFactoryUtil.getUserServiceRedis();
            list = jedis.hmget(key,field);
        } catch (Exception e) {
            RedisFactoryUtil.returnBrokenJedis(jedis);
        } finally {
            RedisFactoryUtil.returnJedis(jedis);
        }
        return list;
    }

    public static Long hdel(String key,String field){
        long length = 0;
        Jedis jedis = null;
        try {
            jedis = RedisFactoryUtil.getUserServiceRedis();
            length = jedis.hdel(key,field);
        } catch (Exception e) {
            RedisFactoryUtil.returnBrokenJedis(jedis);
        } finally {
            RedisFactoryUtil.returnJedis(jedis);
        }
        return length;
    }


}
