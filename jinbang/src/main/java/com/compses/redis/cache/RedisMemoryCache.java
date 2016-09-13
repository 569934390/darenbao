package com.compses.redis.cache;


import com.compses.redis.domain.PushEnable;

import java.util.HashMap;
import java.util.Map;


public class RedisMemoryCache {

	private static final Map<String,PushEnable> redisPushEnableCache = new HashMap<String,PushEnable>();
	private static final Map<String,Object> redisCache = new HashMap<String,Object>();
	public final static String PUBLISH_SUBSCRIBE_KEY="push_publish_subscribe_key";
	public final static String PUSH_STAFF_ENABLE_KEY="pushStaffEnable";
	public final static String PUSH_USER_ENABLE_KEY="pushUserEnable";
	public final static long limitTime = 1000l*60*1;
	/**
	 * 后台判断帮助者端是否启动轮询
	 * @return
	 */
	public static Boolean isPushStaffEnable(){
		PushEnable bool  = redisPushEnableCache.get(PUSH_STAFF_ENABLE_KEY);
		if(bool==null){
			return false;
		}else{
			return  toTrueAndFalse( bool);
		}
	}
	
	
	/**
	 * APP端判断帮助者端是否启动轮询
	 * @return
	 */
	public static Boolean isPushStaffAPPEnable(){
		PushEnable bool  = redisPushEnableCache.get(PUSH_STAFF_ENABLE_KEY);
		if(bool==null){
			return false;
		}else{
			return  bool.isEnable();
		}
	}
	
	/**
	 * 后台判断
	 * @param bool
	 * @return
	 */
	private static boolean toTrueAndFalse(PushEnable bool){
//		long curr = System.currentTimeMillis();
//		if(bool.isEnable()&& ((curr-bool.getCreateTime())>limitTime)){
//			return true;
//		}
		
		return bool.isEnable();
	}
	
	/**
	 * 后台判断用户端是否启动轮询
	 * @return
	 */
	public static Boolean isPushUserEnable(){
		PushEnable bool  = redisPushEnableCache.get(PUSH_USER_ENABLE_KEY);
		if(bool==null){
			return false;
		}else{
			return toTrueAndFalse( bool);
		}
	}
	
	
	/**
	 * APP端判断用户端是否启动轮询
	 * @return
	 */
	public static Boolean isPushUserAPPEnable(){
		PushEnable bool  = redisPushEnableCache.get(PUSH_USER_ENABLE_KEY);
		if(bool==null){
			return false;
		}else{
			return bool.isEnable();
		}
	}
	
	/**
	 * 设置缓存的值
	 * @param key
	 * @param value
	 */
	public static void set(String key,long time,Boolean value){
		PushEnable pushEnable = new PushEnable();
		pushEnable.setCreateTime(time);
		pushEnable.setEnable(value);
		redisPushEnableCache.put(key, pushEnable);
	}
	
	/**
	 * 设置缓存的值
	 * @param key
	 * @param value
	 */
	public static void set(String key,Boolean value){
		set(key, System.currentTimeMillis(), value);
	}
	
	/**
	 * 保存redis信息
	 * @param key
	 * @param value
	 */
	public static  void setRedisCache(String key,Object value){
		redisCache.put(key, value);
	}
	
	/**
	 * 得到对应的值
	 * @param key
	 * @return
	 */
	public  static Object getRedisCache(String key ){
		return  redisCache.get(key);
	}
}
