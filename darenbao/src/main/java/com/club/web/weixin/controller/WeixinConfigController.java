package com.club.web.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.util.WeixinConfigUtil;

/**
* @Title: WeixinShareUtil.java 
* @Package com.club.web.weixin.util 
* @Description: TODO(微信Config Url验证) 
* @author 柳伟军   
* @date 2016年5月13日 上午9:56:03 
* @version V1.0
 */
@Controller
@RequestMapping("/weixin/weixinConfig")
public class WeixinConfigController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired WeixinConfigUtil weixinConfigUtil;
	
	/**
	 * 验证
	 * @param url 绝对地址：如index.html
	 * @return
	 */
	@RequestMapping("/verification")
	@ResponseBody
	public Map<String, Object> verification(String url, HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result=new HashMap<String,Object>();
		try {
			result.put("success", true);
			result.put("msg", weixinConfigUtil.verification(url));
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "验证失败！");
			logger.info("微信config验证失败 ：error  {} " + e.getMessage());
		}
		return result;
	}
	/**
	 * 验证
	 * @param url
	 * @return
	 */
	@RequestMapping("/verificationUrl")
	@ResponseBody
	public Map<String, Object> verificationUrl(String url, HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result=new HashMap<String,Object>();
		try {
			result.put("success", true);
			result.put("msg", weixinConfigUtil.verificationUrl(url));
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "验证失败！");
			logger.info("微信config验证失败  verificationUrl ：error  {} " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 验证
	 * @param url 绝对地址：如index.html
	 * @return
	 */
	@RequestMapping("/getToken")
	@ResponseBody
	public Map<String, Object> getToken(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result=new HashMap<String,Object>();
		try {
			result.putAll(weixinConfigUtil.getToken());
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "获取失败！");
			logger.info("微信token验证失败 ：error  {} " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 验证
	 * @param url 绝对地址：如index.html
	 * @return
	 */
	@RequestMapping("/getAccessToken")
	@ResponseBody
	public Map<String, Object> getAccessToken(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result=new HashMap<String,Object>();
		try {
			result.put("success", true);
			result.put("access_token", WeixinGlobal.accessToken);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "获取失败！");
			logger.info("微信accessToken验证失败 ：error  {} " + e.getMessage());
		}
		return result;
	}

}
