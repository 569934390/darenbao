package com.club.framework.util;

import java.util.List;
import java.util.Map;

public class ValueUtil {
	
	/**
	 * 从Map中获取字符串值
	 * @param m
	 * @param key
	 * @return
	 */
	public static String getStrValue(Map<String,Object> m,String key){
		Object obj = m.get(key);
		String retStr = "";
		if(null != obj){
			retStr = (String)obj;
		}
		return retStr.trim();
	}
	
	/**
	 * 从Map中获取整型值
	 * @param m
	 * @param key
	 * @return
	 */
	public static int getIntValue(Map<String,Object> m,String key){
		Object obj = m.get(key);
		int ret = 0;
		if(null != obj){
			String s = (String)obj;
			ret = Integer.parseInt(s.trim());
		}
		return ret;
	}
	
	/**
	 * 判断Map中是否存在某个KEY的值
	 * @param m
	 * @param key
	 * @return
	 */
	public static boolean containValue(Map<String,Object> m,String key){
		return m.get(key) != null;
	}
	/**
	 * 判断某个对象是否为空
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj){
		boolean isEmpty = true;
		if(obj instanceof List){
			List list = (List) obj;
			if(null != list && list.size() > 0){
				isEmpty = false;
			}
		}else if(obj instanceof Map){
			Map map = (Map) obj;
			if(null != map && !map.isEmpty()){
				isEmpty = false;
			}
		}else if(obj instanceof String){
			String s = (String)obj;
			if(!"".equals(s)){
				isEmpty = false;
			}
		}
		return isEmpty;
	}
	/**
	 * 判断一个数组是否为空
	 * @param array
	 * @return
	 */
	public static boolean isArrayEmpty(Object[] array){
		boolean isArrayEmpty = true;
		if(null != array && array.length > 0){
			isArrayEmpty = false;
		}
		return isArrayEmpty;
	}
}
