package com.compses.redis.util;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisSessionUtil {

	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			value = jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	

	/**
	 * redis缓存keys数据
	 * @param key
	 * @return
	 */
	public static Set<byte[]> keys(String key) {
		Set<byte[]> value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			value = jedis.keys(key.getBytes());
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
	public static byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
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
	public static void del(byte[] key) {
		 
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			 jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	/**
	 *  redis缓存set设置数据
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		 set(  key,   value,0);
	}
	
	/**
	 *  redis缓存set设置数据
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value,int seconds) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			jedis.set(key, value);
			if (seconds > 0) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 *  redis缓存set设置数据
	 * @param key
	 * @param value
	 */
	public static void set(byte key[], byte value[],int seconds) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			jedis.set(key, value);
			if (seconds > 0) {
				jedis.expire(key, seconds);
			}
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
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			return jedis.exists(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return false;
	}
	
	/**
	 * flush
	 */
	public static  void flushDB(){
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			jedis.flushDB();
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	/**
	 * size
	 */
	public static Long dbSize(){
		Long dbSize = 0L;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getManagerSessionRedis();
			dbSize = jedis.dbSize();
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return dbSize;
	}

}
