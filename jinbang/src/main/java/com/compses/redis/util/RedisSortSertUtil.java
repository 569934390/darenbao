package com.compses.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashSet;
import java.util.Set;

public class RedisSortSertUtil {
	
	/** 
	 * 接单达人
	 */
	public static final String ORDER="order";
	
	/** 
	 * 收入达人
	 */
	public static final String MONEY="money";
	
	/** 
	 * 热门区域
	 */
	public static final String HOTSPOT="hotspot";
	
	
	/** 
	 * 统计城市
	 */
	public static final String SUM_CITY_ZET="sum_city_zet";
	
	public static Jedis getDB(){
		return RedisFactoryUtil.getSumRedis();	
	}
//	
//	/**
//	 * 添加
//	 * 
//	 * @param key 存放key
//	 * @param value 存放值
//	 */
//	public static void zadd(String key,double score,String member) {
//		 Jedis jedis=null;
//		try {
//			jedis=getDB();
//			jedis.zadd(key, score, member);
//		} catch (Exception e) {
//			RedisFactoryUtil.returnBrokenJedis(jedis);
//		} finally {
//			RedisFactoryUtil.returnJedis(jedis);
//		}
//	}
	
	/**
	 * 添加成员
	 * 
	 * @param key
	 * @param member
	 */
	public static void saddCity(String key,String member) {
		 Jedis jedis=null;
		try {
			jedis=getDB();
			jedis.sadd(key, member);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	/**
	 * 获取所有成员
	 * 
	 * @param key
	 */
	public static Set<String> smembersCitys(String key) {
		 Jedis jedis=null;
		 Set<String> set = new HashSet<String>();
		try {
			jedis=getDB();
			set=jedis.smembers(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return set;
	}
	
	/**
	 * 减score
	 * 
	 * @param key
	 * @param score
	 * @param member
	 */
	public static void reduceZincr(String key,String member){
		zincrby(key, -1, member);
	}
	
	/**
	 * 增加score
	 * 
	 * @param key
	 * @param score
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
			jedis=getDB();
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
				jedis=getDB();
				return jedis.zscore(key, member);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return 0;
	}
	/**
	 * 递减排序
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return 
	 */
	public static Set<Tuple> zrevrange(String key,long start,long end) {
		 Jedis jedis=null;
		try {
				jedis=getDB();
				return jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
			e.printStackTrace();
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return null;
	}
	
	/**
	 * 并集
	 * 
	 * @param key
	 * @param score
	 * @param member
	 */
	public static void zunionstore(String key,String... sets) {
		Jedis jedis=null;
		try {
			jedis=getDB();
			jedis.zunionstore(key, sets);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
	
	
	
	/**
	 * del
	 * 
	 * @param key 删除key
	 *  
	 */
	public static void del(String key) {
		 Jedis jedis=null;
		try {
			jedis=getDB();
			jedis.del(key);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
	}
}
