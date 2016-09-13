package com.compses.redis.util;

import com.compses.redis.cache.RedisMemoryCache;
import com.compses.redis.service.impl.PushModifyListener;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisPollingUtils {
	private static PushModifyListener listener = new PushModifyListener() ;
	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static List<String> lpop(String key) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getPollingRedis();
			value = jedis.lrange(key, 0, -1);
			if(value!=null){
				int size = value.size();
				jedis.ltrim(key, size, -1);
				if(size>10){
					value = value.subList(size-10, size);
				}
			}
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
	public static void rpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getPollingRedis();
			jedis.rpush(key, value);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static String get(String key) throws Exception {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getPollingRedis();
			value = jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
			throw e;
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
	public static List<String> mget(String... keys) throws Exception {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getPollingRedis();
			value = jedis.mget(keys);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
			throw e;
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
			jedis = RedisFactoryUtil.getPollingRedis();
			jedis.set(key, value);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 订阅消息
	 */
	public static void subscribe() {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getPollingRedis();
			jedis.subscribe(listener, RedisMemoryCache.PUBLISH_SUBSCRIBE_KEY);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 退订消息
	 */
	public static void unubscribe() {
		 
		try {
			if(listener!=null){
				listener.unsubscribe(RedisMemoryCache.PUBLISH_SUBSCRIBE_KEY);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			 
		}
	}
	
	
	/**
	 * 发布消息
	 */
	public static void publish(String channel,String message) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getPollingRedis();
			jedis.publish(channel, message);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
}
