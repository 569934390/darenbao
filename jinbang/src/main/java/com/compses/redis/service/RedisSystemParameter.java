package com.compses.redis.service;

import redis.clients.jedis.Jedis;

import java.util.Map;

public interface RedisSystemParameter {

	public void init(Jedis jedis);
	
	public void update(Jedis jedis, Map<String, Object> data);
	
	
}
