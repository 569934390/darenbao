package com.compses.redis.service.impl;

import com.compses.redis.cache.RedisMemoryCache;
import com.compses.redis.service.RedisSystemParameter;
import com.compses.redis.util.RedisPollingDataUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Service
public class RedisSystemJPushParameterImpl implements RedisSystemParameter {

	@Override
	public void init(Jedis jedis) {
		try {
			String userEnable = RedisPollingDataUtils.getPushUserEnable();
			String staffEnable = RedisPollingDataUtils.getPushStaffEnable();
			long time = System.currentTimeMillis()-RedisMemoryCache.limitTime-RedisMemoryCache.limitTime;
			if (staffEnable != null) {
				int pushStaffEnable = Integer.parseInt(staffEnable);
				if (pushStaffEnable == 0) {
					RedisMemoryCache.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY,time, true);
				} else {
					RedisMemoryCache.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY,time, false);
				}

			}

			if (userEnable != null) {
				int pushUserEnable = Integer.parseInt(userEnable);
				if (pushUserEnable == 0) {
					RedisMemoryCache.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY,time,true);
				} else {
					RedisMemoryCache.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY,time,false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Jedis jedis, Map<String, Object> data) {
		if(data!=null && data.size()>0){
			Object opushStaffEnable=data.get(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY);
			Object opushUserEnable=data.get(RedisMemoryCache.PUSH_USER_ENABLE_KEY);
			 if(opushStaffEnable!=null){
				 int  pushStaffEnable =  Integer.parseInt(opushStaffEnable.toString());
				 if(pushStaffEnable==0){
					 RedisMemoryCache.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY, true);
				 }else{
					 RedisMemoryCache.set(RedisMemoryCache.PUSH_STAFF_ENABLE_KEY, false);
				 }
			 }
			 
			 if(opushUserEnable!=null){
				 int  pushUserEnable =  Integer.parseInt(opushUserEnable.toString());
				 if(pushUserEnable==0){
					 RedisMemoryCache.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY, true);
				 }else{
					  RedisMemoryCache.set(RedisMemoryCache.PUSH_USER_ENABLE_KEY, false);
				 }
			 }
		}
	}

}
