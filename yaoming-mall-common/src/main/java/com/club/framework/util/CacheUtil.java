package com.club.framework.util;

import org.apache.log4j.Logger;

import com.club.framework.vo.CacheData;

/**
 * 缓存工具:系统初始化时将数据缓存
 * @author WUWY
 *
 */
public class CacheUtil {
	private static Logger logger = Logger.getLogger(CacheUtil.class);
	
	private CacheUtil(){}
	
	public static void putCache(String catagory,String key,Object value){
		CacheData.setCacheData(catagory, key, value);
	}
	
	public static Object getCache(String catagory,String key){
		return CacheData.getCache(catagory, key);
	}
	
	public static void clearCache(){
		logger.info("Clear cache all cache!");
		CacheData.clear();
	}
	
	public static void clearCache(String catagory){
		logger.info("Clear cache "+catagory+" cache!");
		CacheData.clear(catagory);
	}
	
}
