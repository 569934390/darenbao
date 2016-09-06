package com.club.web.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.alipay.service.AlipayService;
import com.club.web.deal.service.IndentService;
import com.club.web.weixin.service.ShanGuoWxpayService;
import com.club.web.weixin.util.RequestHandler;
import com.club.web.wxpay.util.MessageUtil;

/**
 * @Title: WeixinPayController.java
 * @Package com.club.web.weixin.controller
 * @Description: TODO(微信支付Controller)
 * @author 柳伟军
 * @date 2016年5月4日 下午5:44:38
 * @version V1.0
 */
@Controller
@RequestMapping("/weixin/weixinPay")
public class WeixinPayController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ShanGuoWxpayService shanGuoWxpayService;

	@Autowired
	IndentService indentService;

	/**
	 * 微信支付
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxPay")
	@ResponseBody
	public Map<String, Object> getWxPayInfo(Long orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(shanGuoWxpayService.getWxPayInfo(orderId, request, response));
		} catch (Exception e) {
			result.put("code", false);
			result.put("msg", e.getMessage());
			logger.error("支付统一下单失败！msg: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * 微信退款
	 * 
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxRePay")
	@ResponseBody
	public Map<String, Object> WxRePay(Long orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(shanGuoWxpayService.WxRePay(orderId, request, response));
		} catch (Exception e) {
			result.put("code", false);
			result.put("msg", e.getMessage());
			logger.error("退款申请失败！msg: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * 微信支付回调
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wxPayCallBack")
	public String PayCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/xml;charset=utf-8");
		String result = null;
		try {
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			shanGuoWxpayService.payCallBack(requestMap);
			response.getWriter().write(RequestHandler.setXML("SUCCESS", "OK"));
		} catch (Exception e) {
			logger.error("回调失败！msg: {}", e.getMessage());
			response.getWriter().write(RequestHandler.setXML("FAIL", "处理失败 "));
			e.printStackTrace();
		} 
		return null;
	}

	/**
	 * 微信支付回调测试
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/testPayCallBack")
	public String testPayCallBack(String orderId, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		String result = null;
		try {
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("out_trade_no", orderId);
			requestMap.put("result_code", "SUCCESS");
			requestMap.put("total_fee", "100");
			requestMap.put("openid", "osH01wkMxnubin3iEt1llJiTdK9M");
			shanGuoWxpayService.payCallBack(requestMap);
			response.getWriter().write(RequestHandler.setXML("SUCCESS", "OK"));
		} catch (Exception e) {
			logger.error("回调失败！msg: {}", e.getMessage());
			response.getWriter().write(RequestHandler.setXML("FAIL", "处理失败 "));
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 微信取消订单
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxCloseOrder")
	@ResponseBody
	public Map<String, Object> wxCloseOrder(Long orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(shanGuoWxpayService.wxCloseOrder(orderId, request, response));
		} catch (Exception e) {
			result.put("code", false);
			result.put("msg", e.getMessage());
			logger.error("取消订单失败！msg: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * 测试入口
	 * 
	 * @return
	 */
	@RequestMapping("/testpay")
	public String testPay() {

		return "/test/pay";
	}
}
