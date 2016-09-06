package com.club.framework.util;

import com.club.framework.filter.SQLFilter;

public class SecurityUtil {

	/**
	 * 替换敏感字符
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanXss(String value) {
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}

	/**
	 * 是否包含待过滤的SQL字符
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isContainedSQL(String value) {
		value = value.toLowerCase();// 统一转为小写
		// "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|master|truncate"
		String badStr = "exec |exec&nbsp;|execute |execute&nbsp;|insert |insert&nbsp;|select |select&nbsp;|delete |delete&nbsp;|"
				+ "update |update&nbsp;|count |count&nbsp;|drop |drop&nbsp;|master |master&nbsp;|truncate |truncate&nbsp;|"
				+ "declare |declare&nbsp;|sitename |sitename&nbsp;|net user |net&nbsp;user&nbsp;|xp_cmdshell |xp_cmdshell&nbsp;|"
				+ "like'|like&#39;|insert |insert&nbsp;|create |create&nbsp;|drop |drop&nbsp;|from |from&nbsp;|grant |grant&nbsp;|"
				+ "group_concat |group_concat&nbsp;|column_name|information_schema.columns|table_schema |table_schema&nbsp;|"
				+ "union |union&nbsp;|where |where&nbsp;|order by|order&nbsp;by";// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (value.indexOf(badStrs[i]) >= 0) {
//				System.out.println("===============================");
//				System.out.println("===============================sql过滤："+badStrs[i]);
//				System.out.println("===============================");
				if(badStrs[i].contains("&nbsp;")){
					badStrs[i] = badStrs[i].replace("&nbsp;", " ");
				}
				SQLFilter.badStr = badStrs[i];
				return true;
			}
		}
		return false;
	}
}
