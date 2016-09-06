package com.club.web.weixin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信支付服务
 * 
 * @author hqLin
 */
public interface ShanGuoWxpayService {
	
	Map<String, Object> getWxPayInfo(Long orderId,HttpServletRequest request, HttpServletResponse response) throws Exception;
	

	public Map<String, Object> WxRePay(Long orderId,HttpServletRequest request, HttpServletResponse response) throws Exception;

	void payCallBack(Map<String, String> requestMap) throws Exception;


	Map<String,Object> wxCloseOrder(Long orderId, HttpServletRequest request,
			HttpServletResponse response);
}
