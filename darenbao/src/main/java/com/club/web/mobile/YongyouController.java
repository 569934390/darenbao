package com.club.web.mobile;

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
import com.club.web.finance.service.FinanceAccountspayService;

/**
 * @Title: YongyouController.java
 * @Package com.club.web.mobile
 * @Description: TODO(用友对接接口)
 * @author 柳伟军
 * @date 2016年7月25日 上午10:19:53
 * @version V1.0
 */
@RequestMapping("/mobile")
@Controller
public class YongyouController {

	private Logger logger = LoggerFactory.getLogger(YongyouController.class);

	@Autowired
	FinanceAccountspayService financeAccountspayService;

	/**
	 * 测试
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/yongyou/test")
	@ResponseBody
	public Map yongyouTest(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.error("yongyouTest YongyouController msg -> {}", JsonUtil.toJson(request.getParameterMap()));
		return request.getParameterMap();
	}

	/**
	 * 销售订单
	 * 
	 * @param fanceId
	 * @param yongYouOrderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/yongyou/order")
	@ResponseBody
	public Map<String, Object> yongYouOrder(String fanceId, String yongYouOrderId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			result.putAll(financeAccountspayService.updateByU8Order(fanceId, yongYouOrderId));
		} catch (Exception e) {
			logger.error("U8销售单异常<YongyouController yongYouOrder>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 收付款单
	 * 
	 * @param fanceId
	 * @param yongYouAccountId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/yongyou/account")
	@ResponseBody
	public Map<String, Object> yongYouAccount(String fanceId, String yongYouAccountId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			result.putAll(financeAccountspayService.updateByU8Account(fanceId, yongYouAccountId));
		} catch (Exception e) {
			logger.error("U8收款单异常<YongyouController yongYouAccount>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

}
