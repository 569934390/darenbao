package com.compses.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import com.compses.common.Constants;

public class StringHelper {
	public static Object findListValueByFieldName(List<?> list,String key,Object value){
		Object returnObj=null;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if(object instanceof Map){
				Map map=(Map)object;
				if(map.get(key).equals(value)){
					returnObj=object;
					break;
				}
			}else{
				try {
					Object propertyValue=BeanUtils.getProperty(object,key);
					if(propertyValue.equals(value)){
						returnObj=object;
						break;
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return returnObj;
	}
	
	/**
	 * 检查字符串是否为空.值为null或长度为0都为空
	 * 
	 * 或者字符串的值与NULL_INT,NULL_LONG等相等时也认为为空
	 * 主要是为了区别基本数据类型的0不是空
	 * @param val String 要检查的字符串
	 * @return boolean true-为空, false-不为空

	 */
	public static boolean isEmptyForObject(Object obj) {
		if(obj==null) return true;
		String val=obj.toString();
		if (val == null || val.length() == 0) {
			return true;
		}
		try {
			if (Integer.parseInt(val) == Constants.NULL_INT) {
				return true;
			}
		} catch (Exception e) {
		}
		try {
			if(Long.parseLong(val)==Constants.NULL_LONG){
				return true;
			}
		} catch (Exception e) {
		}
		try {
			if(Float.parseFloat(val)==Constants.NULL_FLOAT){
				return true;
			}
		} catch (Exception e) {
		}
		try {
			if(Double.parseDouble(val)==Constants.NULL_DOUBLE){
				return true;
			}
		} catch (Exception e) {
		}
		if(obj instanceof Collection){
			return ((Collection) obj).isEmpty();
		}
		
		return false;
	}


}
