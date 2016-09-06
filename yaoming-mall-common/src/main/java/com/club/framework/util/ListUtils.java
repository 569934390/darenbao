package com.club.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.club.framework.log.ClubLogManager;

/**
 * <Description> List数组帮助类<br>
 */
public abstract class ListUtils {
	private final static ClubLogManager logger = ClubLogManager.getLogger(ListUtils.class);

	
	public static final String SPLIT = ",";//默认间隔符
	
	/**
	 * 判断col是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list) {
		return list == null || list.size() == 0;
	}
	
	public static boolean isNotEmpty(List list) {
		return !isEmpty(list);
	}
	
	/**
	 * 字符串转Long列表
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static List<Long> strToLongList(String str) {
		return strToLongList(str,SPLIT);
	}
	public static List<Long> strToLongList(String str, String split) {
		List<Long> list = new ArrayList<Long>();
		if(StringUtils.isEmpty(split)){
			split = SPLIT;
		}
		try {
			if (StringUtils.isNotEmpty(str)) {
				String[] newStrs = str.split(split);
				for (String string : newStrs) {
					list.add(Long.valueOf(string));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 字符串转Long数组
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static Long[] strToLongs(String str) {
		return strToLongs(str,SPLIT);
	}
	public static Long[] strToLongs(String str, String split) {
		Long[] list = null;
		try {
			if(StringUtils.isEmpty(split)){
				split = SPLIT;
			}
			if (StringUtils.isNotEmpty(str)) {
				String[] newStrs = str.split(split);
				list = new Long[newStrs.length];
				for (int i = 0; i < newStrs.length; i++) {
					list[i] = Long.valueOf(newStrs[i]);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	/**
	 * 字符串转String列表
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static List<String> strToStrList(String str) {
		return strToStrList(str,SPLIT);
	}
	public static List<String> strToStrList(String str, String split) {
		List<String> list = new ArrayList<String>();
		try {
			if(StringUtils.isEmpty(split)){
				split = SPLIT;
			}
			if (StringUtils.isNotEmpty(str)) {
				String[] newStrs = str.split(split);
				list = Arrays.asList(newStrs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
}
