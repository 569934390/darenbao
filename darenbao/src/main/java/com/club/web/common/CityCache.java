package com.club.web.common;

import com.club.framework.util.StringUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/11.
 */
public class CityCache {
    private static int CACHE_EXPIRE_MINUTE=8;
    private static LoadingCache<String,String> cache=CacheBuilder.newBuilder()
            .expireAfterAccess(CACHE_EXPIRE_MINUTE, TimeUnit.MINUTES)
            .build(new CacheLoader<String , String>() {
                @Override
                public String load(String s) throws Exception {
                    String verifyCode = StringUtils.random(4);
                    return verifyCode;
                }
            });
    public static String getNativeCityName(String nativeCityCode){
        return cache.getUnchecked(nativeCityCode);
    }
    static {

        Timer  timer=new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                cache.cleanUp();
            }
        };
        timer.schedule(task,0,(CACHE_EXPIRE_MINUTE-1)*60*1000l);
    }

    public static void main(String[] args) throws ExecutionException {
        System.out.println(CityCache.cache.get("18978833622"));
    }
}
