/**
 * 
 */
package com.club.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;

/**
 * 
 * 公共方法类
 * 
 * @author Administrator
 *
 */
public class CommonUtil {
	/**
	 * 将List<T>对象转换成List<Map<String, Object>>
	 * 
	 * @param t
	 * @add by 2016-03-23
	 */
	public static <T> List<Map<String, Object>> listObjTransToListMap(List<T> t) {
		List<Map<String, Object>> listMap = null;
		if (t != null && t.size() > 0) {
			listMap = new ArrayList<Map<String, Object>>();
			for (T r : t) {
				listMap.add(JsonUtil.toMap(r));
			}
		}
		return listMap;
	}

	/**
	 * 判断是否全部为数字-整数：^-?\\d+$-负整数：^-[0-9]*[1-9][0-9]*$-正整数:^[0-9]*[1-9][0-9]*$-
	 * 非负整数
	 * :^\\d+$-非正整数:^((-\\d+)|(0+))$-非负浮点数:^\\d+(\\.\\d+)?$-正浮点数:^(([0-9]+\\.
	 * [0-
	 * 9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$-负浮点数
	 * :^(
	 * -(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][
	 * 0-9]*)))$-浮点数:^(-?\\d+)(\\.\\d+)?$-非正浮点数:^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?)
	 * )$
	 */
	public static boolean isDigital(String str) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(str)) {
			flag = str.matches("^\\d+$");
		}
		return flag;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}?-?[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * MD5加密
	 * 
	 * @throws NoSuchAlgorithmException
	 * 
	 * @add by 2016-03-15
	 */
	public static String md5(String source) throws NoSuchAlgorithmException {
		String md5Str = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(source.getBytes());
		byte tmp[] = messageDigest.digest();
		// 每个字节用16进制表示的话，使用两个字符，所以表示成 16进制需要32 个字符
		char str[] = new char[16 * 2];
		// 表示转换结果中对应的字符位置
		int k = 0;
		// 从第一个字节开始，对 MD5 的每一个字节，转换成 16 进制字符的转换
		for (int i = 0; i < 16; i++) {
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		md5Str = new String(str);
		return md5Str;
	}

	/**
	 * 隐藏字符串位数
	 * 
	 * @param hideParam
	 *            -隐藏值
	 * @param startIndex
	 *            -显示前几位
	 * @param lastIndex
	 *            -显示后几位
	 * @param count
	 *            -隐藏替换位数
	 * @add by 2016-05-25
	 */
	public static String getHideIdCardVal(String hideParam, int prevIndex, int lastIndex, int count) {
		StringBuilder str = new StringBuilder();
		if (StringUtils.isNotEmpty(hideParam)) {
			str = str.append(hideParam.substring(0, prevIndex));
			for (int i = 0; i < count; i++) {
				str = str.append("*");
			}
			str = str.append(hideParam.substring(hideParam.length() - lastIndex));
		}
		return str.toString();
	}
	/**
	 * 产生num位的随机数
	 * 
	 * @return
	 */
	public static String getRandByNum(int num) {
		String length = "1";
		for (int i = 0; i < num; i++) {
			length += "0";
		}
		Random rad = new Random();
		String result = rad.nextInt(Integer.parseInt(length)) + "";
		if (result.length() != num) {
			return getRandByNum(num);
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println(isPhone("0592-2176495"));
		System.out.println(isPhone("0592217641"));
	}



}
