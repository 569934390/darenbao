package com.compses.redis.util;


import redis.clients.jedis.Jedis;

public class ReidsListUtil {
 
	/**
	 *  存入对列
	 * @param key 存放key
	 * @param value 存放值
	 * @param isExpire 是否加过期时间
	 * @param time 以秒为单位
	 */
	public static void push(String key,String value,boolean isExpire,int time) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getGrabRedis();
			jedis.lpush(key, value);
			if(isExpire){
				jedis.expire(key,time);
			}
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	/**
	 *  存入对列
	 * @param key 存放key
	 * @param value 存放值
	 * @param isExpire 是否加过期时间
	 * @param time 以秒为单位
	 */
	public static void push(String key,String value[],boolean isExpire,int time) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getGrabRedis();
			jedis.lpush(key, value);
			if(isExpire){
				jedis.expire(key,time);
			}
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 存入对列
	 * @param key 存放key
	 * @param value 存放值
	 */
	public static void push(String key, String value) {
		push(key, value, false, 0);
	}
	
	
	/**
	 * 获取对列长度
	 * @param key 存放key
	 */
	public static long len(String key) {
		Jedis jedis = null;
		long value=0;
		try {
			jedis = RedisFactoryUtil.getGrabRedis();
			value =jedis.llen(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	
	/**
	 * 出对列
	 * @param key 读取key
	 * @return
	 */
	public static String pop(String key) {
		Jedis jedis = null;
		String orderCode = null;
		try {
			jedis = RedisFactoryUtil.getGrabRedis();
			orderCode = jedis.rpop(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return orderCode;
	}
	
	/**
	 * 判断多久同一个KEY被操作
	 * @param KEY 
	 * @return 如果是true可以操作，false不可操作
	 */
	public static boolean isOpertor(String key){
		return isOpertor(  key,  40);
	}
	
	/**
	 * 判断多久同一个KEY被操作
	 * @param KEY 
	 * @return 如果是true可以操作，false不可操作
	 */
	public static boolean isOpertor(String key,int expire){
		Jedis jedis = null;
		boolean isbool = false;
		try {
			jedis = RedisFactoryUtil.getGrabRedis();
			if(!jedis.exists(key)){
				if(expire>0){
					jedis.setex(key, expire, "1");
				}else{
					jedis.set(key,"1");
				}
				return true;
				
			}
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return isbool;
	}
	
	/**
	 * 判断是否存在
	 * @param key 读取key
	 * @return
	 */
	public static boolean exist(String key) {
		Jedis jedis = null;
		boolean isbool = false;
		try {
			jedis = RedisFactoryUtil.getGrabRedis();
			isbool = jedis.exists(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return isbool;
	}
}
