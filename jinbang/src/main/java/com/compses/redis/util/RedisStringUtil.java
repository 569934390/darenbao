package com.compses.redis.util;

import com.compses.util.JsonUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class RedisStringUtil {

	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getRedis();
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
	public static byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getRedis();
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
			jedis = RedisFactoryUtil.getRedis();
			 jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}

	/**
	 * 删除redis
	 * @param key
	 */
	public static void del(String key){
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getRedis();
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
			jedis = RedisFactoryUtil.getRedis();
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
			jedis = RedisFactoryUtil.getRedis();
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
			jedis = RedisFactoryUtil.getRedis();
			return jedis.exists(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return false;
	}
	
	
	
	/**
	 * redis缓存get数据
	 * @param key
	 * @return
	 */
	public static String getOrder(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAutoCodeRedis();
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
	public static String getOrderAndEXception(String key)throws Exception {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAutoCodeRedis();
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
	 *  redis缓存set设置数据
	 * @param key
	 * @param value
	 */
	public static void setOrder(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAutoCodeRedis();
			jedis.set(key, value);
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
	public static boolean existsOrder(String key) {
		Jedis jedis = null;
		try {
			jedis =RedisFactoryUtil.getAutoCodeRedis();
			return jedis.exists(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return false;
	}
	
 
	/**
	 * 自动加1
	 * @param key
	 */
	public static long incrOrder(String key) {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = RedisFactoryUtil.getAutoCodeRedis();
			number = jedis.incr(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}
	
	
	
	/**
	 * 减score
	 * 
	 * @param key
	 * @param member
	 */
	public static void reduceZincr(String key,String member){
		zincrby(key, -1, member);
	}
	
	/**
	 * 增加score
	 * 
	 * @param key
	 * @param member
	 */
	public static void zincr(String key,String member){
		zincrby(key, 1, member);
	}
	
	/**
	 * 增加score
	 * 
	 * @param key
	 * @param score
	 * @param member
	 */
	public static void zincrby(String key,double score,String member) {
		 Jedis jedis=null;
		try {
			jedis=RedisFactoryUtil.getAutoCodeRedis();
			jedis.zincrby(key, score, member);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	/**
	 * 返回member的值
	 * @param key
	 * @param member
	 * @return
	 */
	public static double zscore(String key,String member) {
		 Jedis jedis=null;
		try {
				jedis=RedisFactoryUtil.getAutoCodeRedis();
				Double result = jedis.zscore(key, member);
				if(result!=null){
					return result;
				}
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return 0;
	}

	/**
	 * 得到 验证码
	 * @param key
	 * @return
	 */
	public static String getVCode(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getVerificationCodeRedis();
			value = jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	/** 
	 * 保存验证码
	 * @param key
	 * @param value
	 */
	public static void setVCode(String key,String value){
		setVCode(key, value, 0);
	}
	
	/**
	 * 	保存验证码并让过期
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setVCode(String key,String value,int seconds){
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getVerificationCodeRedis();
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
	 * 删除验证码
	 * 
	 * @param key
	 */
	public static void delVCode(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getVerificationCodeRedis();
			jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 *  得到登录的session信息
	 * @param key
	 * @return
	 */
	public static String getSession(String key) throws Exception {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getSessionRedis();
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
	 *  设置登录的session信息
	 * @param key
	 * @param value
	 */
	public static void setSession(String key,String value){
		setSession(key, value, 0);
	}
	
	
	/**
	 *  设置登录的session信息 并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setSession(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getSessionRedis();
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
	 * 删除登录的session信息
	 * @param key
	 */
	public static void delSession(String key){
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getSessionRedis();
			jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	

	/**
	 *  得到支持单一登录的sessionId信息
	 * @param key
	 * @return
	 */
	public static String getSingleSO(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getSingleSignOnRedis();
			value = jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	
	/**
	 *  设置支持单一登录的sessionId信息
	 * @param key
	 * @param value
	 */
	public static void setSingleSO(String key,String value){
		setSession(key, value, 0);
	}
	/**
	 *  设置支持单一登录的sessionId信息 并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setSingleSO(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getSingleSignOnRedis();
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
	 * 删除支持单一登录的sessionId信息
	 * @param key
	 */
	public static void delSingleSO(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getSingleSignOnRedis();
			jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	
	/**
	 * 限制的量自动加1
	 * @param key
	 */
	public static long incrAllLimit(String key) {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = RedisFactoryUtil.getLimtMobileRedis();
			number = jedis.incr(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}
	
	
	/**
	 * 删除这个限制
	 * @param key
	 */
	public static void delAllLimit(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getLimtMobileRedis();
			 jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	/**
	 *  设置限制数  并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void setAllLimit(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getLimtMobileRedis();
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
	 *  获得设置限制数   
	 * @param key
	 */
	public static String getAllLimit(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getLimtMobileRedis();
			return jedis.get(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return "0";
	}
	
	
	
	
	
	
	/**
	 * redis缓存get已接收推送订单人数
	 * @param key
	 * @return
	 */
	public static String getAcceptOrderOrder(String key) {
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
	 * redis缓存get已接收推送订单人数
	 * @param key
	 * @return
	 */
	public static String getAcceptOrderAndEXception(String key)throws Exception {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
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
	 *  redis缓存set设置已接收推送订单人数
	 * @param key
	 * @param value
	 */
	public static void setAcceptOrder(String key, String value) {
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
	 * 自动加1 已接收推送订单人数
	 * @param key
	 */
	public static long incrAcceptOrder(String key) {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
			number = jedis.incr(key);
			jedis.expire(key, 3600);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}
	
	/**
	 * 自动加1  以expire秒后，失效。
	 * @param key
	 * @param expire
	 */
	public static long incrKey(String key,int expire) {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = RedisFactoryUtil.getAcceptOrderRedis();
			number = jedis.incr(key);
			if(number==1){
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}

	/**
	 * 上传数据到redis
	 * @param key
	 * @param map
	 * @param isMap
	 */
	public static void setRedis(String key,Map map ,boolean isMap){
		if (isMap){
			boolean keyExists =  exists(key);
			if(keyExists){
				String oldValue = get(key);
				Map oldMap = JsonUtils.toMap(oldValue);
				oldMap.putAll(map);
				set(key,JsonUtils.toJson(oldMap));
			}else{
				String value = JsonUtils.toJson(map);
				set(key,value);
			}
		}else {
			set(key, JsonUtils.toJson(map));
		}
	}
}
