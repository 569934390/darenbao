package com.compses.util;

import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.compses.common.Constants;

public class StringUtils {
	
	private static final Pattern numberTemplate = Pattern.compile("\\{(\\d+)\\}");
	private static final Pattern wordTemplate = Pattern.compile("\\$\\{(\\w+)\\}");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String templateStr = "<div id={0} name=${name}>{1}</div>";
		System.out.println(StringUtils.apply(templateStr, 123+"","dfsdf"));
		Map<String,CharSequence> params = new HashMap<String, CharSequence>();
		params.put("name", "OMG");
		System.out.println(StringUtils.apply(templateStr, params));
		System.out.println(StringUtils.apply(templateStr,params, 123+"","dfsdf"));
		System.out.println(StringUtils.firstToLowerCase(""));
		System.out.println(StringUtils.firstToLowerCase("U"));
		System.out.println(StringUtils.firstToLowerCase("u"));
		System.out.println(StringUtils.firstToLowerCase("1"));
		System.out.println(StringUtils.firstToLowerCase("UserController"));
	}

	public static String apply(String str,CharSequence ...arr){
		Matcher m=numberTemplate.matcher(str);
		while(m.find())
			str=str.replace(m.group(),arr[Integer.parseInt(m.group(1))]+"");
		return str;
	}
	
	public static String apply(String str,Object obj){
		return apply(str,convertObjectToMap(obj));
	}
	
	public static String apply(String str,Map<String,CharSequence> params){
		Matcher m = wordTemplate.matcher(str);
		while(m.find())
			str=str.replace(m.group(),params.get(m.group(1)));
		return str;
	}
	
	public static String apply(String str ,Map<String,CharSequence> params,CharSequence ...arr){
		return str = apply(apply(str, params),arr);
	}
	public static String firstToLowerCase(String fieldName){
		if(fieldName.length()>1){
			return fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
		}
		else if(fieldName.length()==1)
			return fieldName.toLowerCase();
		else return fieldName;
	}
	public static boolean isBlank(String str){
		if(str!=null&&!"".equals(str))
			return true;
		else
			return false;
	}
	
	public static Map<String,Object> convertObjectToMap(Object paramObj){
		return JsonUtils.toMap(paramObj);
	}
	
	public static boolean isEmpty(Object val){
		if(val!=null&&!"".equals(val)&&!"null".equals(val))
			return false;
		else
			return true;
	}
	
	public static boolean isEmpty(String val){
		if(val!=null&&!"".equals(val)&&!"null".equals(val))
			return false;
		else
			return true;
	}
	
	public static String join(String[] strs){
		int length=0;
		if(strs==null||(length=strs.length)==0)return "";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append(strs[i]);
			if(i<length-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
    /**  
     * 获取UUID
     * @return string  
     */    
    public static String getUUID(){    
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;    
    }    
    
    /**
	 * 检查对象是否为空.值为null或长度为0都为空
	 * 
	 * 或者字符串的值与NULL_INT,NULL_LONG等相等时也认为为空
	 * 主要是为了区别基本数据类型的0不是空
	 * @return boolean true-为空, false-不为空

	 */
	public static boolean isEmptyForObject(Object obj) {
		if(obj==null) return true;
		String val=obj.toString();
		if (val == null || val.length() == 0||val.equals("null")) {
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
		if(obj instanceof JSONObject ){
			return ((JSONObject) obj).length()==0;
		}
		return false;
	}
	/*
	 * 获取员工staffId，先暂时写死,后续用真实数据替代
	 */
	public static String getStaffId(HttpServletRequest request){
		return "123456";
	}
	
	public static String covertHump(String str){
//		char[] _strs = str.toLowerCase().toCharArray();
		char[] _strs = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i< _strs.length;i++){
			char c = _strs[i];
			if(c=='_'){
				c = _strs[++i];
				sb.append(new String(c+"").toUpperCase());
			}
			else{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String random(int n) {
		if (n < 1 || n > 10) {
			throw new IllegalArgumentException("cannot random " + n + " bit number");
		}
		Random ran = new Random();
		if (n == 1) {
			return String.valueOf(ran.nextInt(10));
		}
		int bitField = 0;
		char[] chs = new char[n];
		for (int i = 0; i < n; i++) {
			while(true) {
				int k = ran.nextInt(10);
				if( (bitField & (1 << k)) == 0) {
					bitField |= 1 << k;
					chs[i] = (char)(k + '0');
					break;
				}
			}
		}
		return new String(chs);
	}

	public static String md5(String str) {
		return md5(str, true);
	}

	public static String md5(String str, boolean isUpper) {
//        MessageDigest md5 = null;
		StringBuilder sb = new StringBuilder();
//        char up = isUpper ? 'X' : 'x';
		try {
//            md5 = MessageDigest.getInstance("MD5");
//            for (byte b : md5.digest(str.getBytes())) {
//                sb.append(String.format("%02" + up, b));
//            }
//    		StringBuffer sb = new StringBuffer(32);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes("utf-8"));
			for (int i = 0; i < array.length; i++)
				if(isUpper)
					sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
				else
					sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3));
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
