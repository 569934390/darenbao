package com.compses.redis.util;


import com.compses.redis.cache.RedisMemoryCache;
import com.compses.util.JsonUtils;

import java.util.List;
import java.util.Map;

public class RedisPollingDataUtils {
	public static final String USER_SESSION_PRE = "U";
	public static final String STAFF_SESSION_PRE = "S";

	private static void savePushData(String pushId, String data, boolean isUser) {
		String key ="";
		if(isUser){
			key=USER_SESSION_PRE+pushId;
		}else{
			key=STAFF_SESSION_PRE+pushId;
		}
		RedisPollingUtils.rpush(key, data);
	}

	/**
	 * 保存用户推送信息
	 * @param pushId
	 * @param data
	 */
	public static void saveUserPushData(String pushId, String data) {
		savePushData(pushId, data, true);
	}
	/**
	 *  保存用户推送信息
	 * @param pushId
	 * @param data
	 */
	public static void saveUserPushData(String pushId, Map<String, Object> data) {
		saveUserPushData(pushId, JsonUtils.objectToJson(data));
	}

	/**
	 *  保存帮助者推送信息
	 * @param pushId
	 * @param data
	 */
	public static void saveStaffPushData(String pushId, String data) {
		savePushData(pushId, data, false);
	}

	/**
	 *  保存帮助者推送信息
	 * @param pushId
	 * @param data
	 */
	public static void saveStaffPushData(String pushId, Map<String, Object> data) {
		saveStaffPushData(pushId, JsonUtils.objectToJson(data));
	}

	private static List<String> getPushData(String pushId,boolean isUser){
		String key ="";
		if(isUser){
			key=USER_SESSION_PRE+pushId;
		}else{
			key=STAFF_SESSION_PRE+pushId;
		}
		return RedisPollingUtils.lpop(key);
	}

	/**
	 * 获取用户的推送
	 * @param pushId
	 * @return
	 */
	public static List<String> getUserPushData(String pushId){
		return getPushData(  pushId,  true);
	}

	/**
	 * 获取帮助者的推送
	 * @param pushId
	 * @return
	 */
	public static List<String> getStaffPushData(String pushId){
		return getPushData(  pushId,  false);
	}

	/**
	 * 设置用户端走轮询
	 * @param value
	 */
	public static void setPushUserEnable(String value){
		RedisPollingUtils.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY, value);
	}

	/**
	 * 设置帮助者端走轮询
	 * @param value
	 */
	public static void setPushStaffEnable(String value){
		RedisPollingUtils.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY, value);
	}


	/**
	 * 得到用户走轮询
	 * @return
	 * @throws Exception
	 */
	public static String getPushUserEnable() throws Exception{
		return RedisPollingUtils.get(RedisMemoryCache.PUSH_USER_ENABLE_KEY);
	}


	/**
	 * 得到帮助者端走轮询
	 * @param value
	 * @throws Exception
	 */
	public static String getPushStaffEnable() throws Exception{
		return RedisPollingUtils.get(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY);
	}

	/**
	 * 得到用户和帮助者端走轮询
	 * @param value
	 * @throws Exception
	 */
	public static List<String> getPushUserStaffEnable() throws Exception{
		return RedisPollingUtils.mget(RedisMemoryCache.PUSH_USER_ENABLE_KEY,RedisMemoryCache.PUSH_STAFF_ENABLE_KEY);
	}

	/**
	 * 发布消息
	 * @param message
	 */
	public static void publish(String message){
		RedisPollingUtils.publish(RedisMemoryCache.PUBLISH_SUBSCRIBE_KEY, message);
	}
}

