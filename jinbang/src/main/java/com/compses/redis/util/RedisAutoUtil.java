package com.compses.redis.util;

import redis.clients.jedis.Jedis;

public class RedisAutoUtil {
	/** 
	 *用户取动态邀请码值
	 */
	public static final String DYNAMIC_INVITATION_CODE="dynamic_invitation_code";
	public static final String DYNAMIC_INVITATION_CODE_STAFF_ID="staffId:";
	public static final String DYNAMIC_INVITATION_CODE_VALUE="code:";
	 
	public static final int HOUR=3600;
	public static final int DAY=86400;
	
	public static Jedis getDB(){
		return RedisFactoryUtil.getInvitationCodeRedis();	
	}
 
	
	/**
	 * 限制的量自动加1
	 * @param key
	 */
	public static long incrInvitationCode() {
		Jedis jedis = null;
		long number = 0l;
		try {
			jedis = getDB();
			number = jedis.incr(DYNAMIC_INVITATION_CODE);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return number;
	}
	
	
	/**
	 *  获得帮助者的邀请码
	 * @param staffId
	 * @return
	 */
	public static String getStaffInvitationCode(String staffId) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getDB();
			value = jedis.get(DYNAMIC_INVITATION_CODE_STAFF_ID+staffId);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	
	/**
	 *  设置帮助者的邀请码
	 * @param staffId
	 * @param invitationCode
	 * @return
	 */
	public static void setStaffInvitationCode(String staffId,String invitationCode) {
		setIC( DYNAMIC_INVITATION_CODE_STAFF_ID+staffId,invitationCode);
	}

	/**
	 *  设置邀请码的帮助者ID  
	 * @param code
	 * @param staffId
	 * @return
	 */
	public static void setInvitationCode(String code,String staffId) {
		setIC(DYNAMIC_INVITATION_CODE_VALUE+code,staffId);
	}
	
	
	/**
	 *  获得邀请码的帮助者ID  
	 * @param code
	 * @return
	 */
	public static String getInvitationCode(String code) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getDB();
			value = jedis.get(DYNAMIC_INVITATION_CODE_VALUE+code);
		} catch (Exception e) {
			RedisFactoryUtil.returnBrokenJedis(jedis);
		} finally {
			RedisFactoryUtil.returnJedis(jedis);
		}
		return value;
	}
	
	
	/**
	 *  设置信息
	 * @param key
	 * @param value
	 */
	private static void setIC(String key,String value){
		set(key, value, 0);
	}
	
	/**
	 *  设置信息 并设置失效时间
	 * @param key
	 * @param value
	 * @param seconds
	 */
	private static void set(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getDB();
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
	
}
