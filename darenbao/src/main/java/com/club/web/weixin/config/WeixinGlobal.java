package com.club.web.weixin.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.google.common.collect.Maps;

/**
 * 微信全局配置类
 */
public class WeixinGlobal {

	/**
	 * 当前对象实例
	 */
	private static WeixinGlobal weixinGlobal = new WeixinGlobal();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("config/weixin.properties");

	/**
	 * 当前accessToken
	 */
	public static String accessToken = "";
	/**
	 * 当前jsapiTicket
	 */
	public static String jsapiTicket = "";

	/**
	 * 获取当前对象实例
	 */
	public static WeixinGlobal getInstance() {
		return weixinGlobal;
	}

	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	public static String getWeixinUrl() {
		return getConfig("weixinUrl");
	}

	public static String getToken() {
		return getConfig("token");
	}

	public static String getAppid() {
		return getConfig("appid");
	}

	public static String getAppsecret() {
		return getConfig("appsecret");
	}

	public static String getPartner() {
		return getConfig("partner");
	}

	public static String getPartnerkey() {
		return getConfig("partnerkey");
	}
	
	public static String getWeixincerPath() {
		return getConfig("weixincerPath");
	}
	
	//获取店铺二维码模板
	public static String getQrcodeStoreTemplate() {
		return getConfig("qrcode.store.template");
	}

	//获取卡券二维码模板
	public static String getQrcodeCouponTemplate() {
		return getConfig("qrcode.coupon.template");
	}
	
	public static void main(String[] args) {
		System.out.println(getWeixincerPath());
	}
}