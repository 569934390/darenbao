package com.compses.redis.util;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
import com.compses.common.Constants;

public class RedisConfig {

    private static final String IP = Constants.getContextProperty("jnuo.redis.ip", "127.0.0.1").toString();
    private static final int PORT = (Integer)Constants.getContextProperty("jnuo.redis.port", 6379);
    private static final String PWD = Constants.getContextProperty("jnuo.redis.pwd","redis123").toString();
    public static int getMaxactive() {
        return 300;
    }
    public static int getMaxidle() {
        return 5;
    }

    public static int getMaxwait() {
        return 10000;
    }

    public static int getTimeout(){
        return 3600;
    }

    public static String  getIp(){
        return IP;
    }

    public static int getPort(){
        return PORT;
    }

    public static String getPwd(){
        return PWD;
    }
}
