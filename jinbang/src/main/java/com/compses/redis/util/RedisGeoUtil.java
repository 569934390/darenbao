package com.compses.redis.util;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class RedisGeoUtil {

    public static GeoCoordinate geoGet(String userId){
        Jedis jedis = null;
        GeoCoordinate geoCoordinate = null;
        try {
            jedis =  RedisFactoryUtil.getRedis();
            List<GeoCoordinate> result =jedis.geopos(RedisKeyConfig.USER_LOCATION_KEY, userId);
            if (result.size()!=0){
                geoCoordinate = result.get(0);
            }
        } catch (Exception e) {
            RedisFactoryUtil.returnBrokenJedis(jedis);
        } finally {
            RedisFactoryUtil.returnJedis(jedis);
        }
        return geoCoordinate;
    }
}
