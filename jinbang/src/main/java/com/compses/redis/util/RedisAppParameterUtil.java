package com.compses.redis.util;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class RedisAppParameterUtil {

	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAppParamRedis();
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
			jedis = RedisFactoryUtil.getAppParamRedis();
			value = jedis.mget(keys);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	/**
	 * redis缓存key数据
	 * @param key
	 * @return
	 */
	public static Set<String> key(String key) {
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAppParamRedis();
			value = jedis.keys(key);
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
			jedis = RedisFactoryUtil.getAppParamRedis();
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
			jedis = RedisFactoryUtil.getAppParamRedis();
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
			jedis = RedisFactoryUtil.getAppParamRedis();
			return jedis.exists(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return false;
	}
}
