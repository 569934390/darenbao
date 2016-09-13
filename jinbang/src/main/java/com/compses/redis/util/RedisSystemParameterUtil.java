package com.compses.redis.util;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisSystemParameterUtil {

	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			value = jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static List<String> mget(String... keys) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			value = jedis.mget(keys);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	/**
	 *  redis缓存set设置数据
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			jedis.set(key, value);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	/**
	 *  redis缓存set设置数据
	 * @param keyValues
	 */
	public static void mset(String... keyValues) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			jedis.mset(keyValues);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 判断key是否存在redis缓存内
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			return jedis.exists(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return false;
	}

	public static void hset(String key,String field ,String value){
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			jedis.hset(key,field,value);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}

	public static List<String> hmget(String key, String...field){
		List<String> list = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
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
			jedis = RedisFactoryUtil.getParamRedis();
			length = jedis.hdel(key,field);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return length;
	}

	public static List<String> hvals(String key){
		List<String> list = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getParamRedis();
			list = jedis.hvals(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return list;
	}
}
