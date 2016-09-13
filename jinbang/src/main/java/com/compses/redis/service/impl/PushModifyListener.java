package com.compses.redis.service.impl;

import com.compses.redis.service.RedisSystemParameter;
import com.compses.redis.util.RedisFactoryUtil;
import com.compses.util.ApplicationUtil;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.OrderComparator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import com.compses.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PushModifyListener extends JedisPubSub {

	private List<RedisSystemParameter> servics;

	public PushModifyListener() {
		Map<String, RedisSystemParameter> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
				ApplicationUtil.getApplicationContext(), RedisSystemParameter.class, true, false);
		if (!matchingBeans.isEmpty()) {
			this.servics = new ArrayList<RedisSystemParameter>(matchingBeans.values());
			OrderComparator.sort(this.servics);
		}
	}
	@Override
	public void onMessage(String channel, String message) {
		if(message!=null && (message.startsWith("{") && message.endsWith("}"))){
			Map<String, Object> data =  JsonUtils.toMap(message);
			Jedis jedis = null;
			try {
				jedis = RedisFactoryUtil.getParamRedis();
				for(RedisSystemParameter rsp:servics){
					rsp.update(jedis, data);
				}
			} catch (Exception e) {
				RedisFactoryUtil.returnBrokenJedis(jedis);
			} finally {
				RedisFactoryUtil.returnJedis(jedis);
			}
		}
/*		
		if(channel.equals(RedisMemoryCache.PUBLISH_SUBSCRIBE_KEY)){
			if(message!=null && (message.startsWith("{") && message.endsWith("}"))){
				 Map<String, Object> data =  JSONUtils.jsonToMap(message);
				Object opushStaffEnable=data.get(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY);
				Object opushUserEnable=data.get(RedisMemoryCache.PUSH_USER_ENABLE_KEY);
				 if(opushStaffEnable!=null){
					 int  pushStaffEnable =  Integer.parseInt(opushStaffEnable.toString());
					 
					 if(pushStaffEnable==0){
						 if(!RedisMemoryCache.isPushUserEnable()){
							 RedisMemoryCache.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY, true);
						 }
					 }else{
						 if(!RedisMemoryCache.isPushUserEnable()){
							 RedisMemoryCache.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY, false);
						 }
					 }
				 }
				 
				 if(opushUserEnable!=null){
					 int  pushUserEnable =  Integer.parseInt(opushUserEnable.toString());
					 if(pushUserEnable==0){
						 if(!RedisMemoryCache.isPushStaffEnable()){
							 RedisMemoryCache.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY, true);
						 }
					 }else{
						 if(!RedisMemoryCache.isPushStaffEnable()){
							 RedisMemoryCache.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY, false);
						 }
					 }
				 }
			}
		}*/
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		

	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		

	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		

	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		

	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		

	}

}
