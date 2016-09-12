package com.compses.redis.util;

import redis.clients.jedis.Jedis;

public class RedisAcceptOrderUtils {
	/**
	 * redis缓存get帮助者每天已接订单数
	 * @param key
	 * @return
	 */
	public static String getStaffAcceptOrder(String key){
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
			value = jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	/**
	 *  redis缓存set设置帮助者每天已接订单数
	 * @param key
	 * @param value
	 */
	public static void setStaffAcceptOrder(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
			jedis.set(key, value);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 自动加1 帮助者每天已接订单数
	 * @param key
	 */
	public static long incrStaffAcceptOrder(String key,int expire) {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
			number = jedis.incr(key);
			jedis.expire(key, expire);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}
	
	
	/**
	 * 自动减1 帮助者每天已接订单数
	 * @param key
	 */
	public static long decrStaffAcceptOrder(String key,int expire) {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
			number = jedis.decr(key);
			jedis.expire(key, expire);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}
}
