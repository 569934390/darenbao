package com.club.framework.vo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.club.framework.exception.FrameException;

public class CacheData {
	private static Map<String,Object> m_Data = new HashMap<String,Object>();
	
	/**
	 * 设置缓存数据
	 * @param catagory
	 * @param id
	 * @param data
	 * @throws FrameException
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void setCacheData(String catagory,String key,Object data){
		Map<String,Object> mData = null;
		Object o = m_Data.get(catagory);
		if(null == o){
			mData = new HashMap<String,Object>();
			m_Data.put(catagory, mData);
		}else{
			mData = (Map<String,Object>)o;
		}
		if(null == mData.get(key)){
			mData.put(key, data);
		}
	}
	
	/**
	 * 获取某个目录下的缓存数据
	 * @param catagory
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getCache(String catagory,String key){
		Object retObj = null;
		Object o = m_Data.get(catagory);
		Map<String,Object> mData = null;
		if(null != o){
			mData = (Map<String,Object>)o;
			retObj = mData.get(key);
		}
		return retObj;
	}
	
	/**
	 * 清空所有缓存
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void clear(){
		Iterator values = m_Data.values().iterator();
		while(values.hasNext()){
			((Map<String,Object>)values.next()).clear();
		}
		m_Data.clear();
	}
	
	/**
	 * 清空指定目录缓存
	 * @param catagory
	 */
	@SuppressWarnings("unchecked")
	public static void clear(String catagory){
		Object o = m_Data.get(catagory);
		Map<String,Object> mData = null;
		if(null != o){
			mData = (Map<String,Object>)o;
			mData.clear();
		}
	}
	
	public static Map<String, Object> getM_Data() {
		return m_Data;
	}

	public static void setM_Data(Map<String, Object> m_Data) {
		CacheData.m_Data = m_Data;
	}
}
