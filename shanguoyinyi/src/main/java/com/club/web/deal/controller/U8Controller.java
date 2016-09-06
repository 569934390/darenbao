package com.club.web.deal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.deal.service.U8InterfaceService;

/**
 * 
 * @author You
 *
 */
@Controller
@RequestMapping("/deal/u8")
public class U8Controller {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private U8InterfaceService  u8InterfaceService;
	
	@RequestMapping("/batch/{type}")
	@ResponseBody
	public String batchU8Deal(@PathVariable String type, HttpServletResponse response) {
		logger.debug("batchU8Deal ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			if (StringUtils.isNotEmpty(type)){
				if(type.equals("order")){
					u8InterfaceService.addU8Order();
				}else{
					u8InterfaceService.addU8Accept();
				}
			}
		} catch (Exception e) {
			logger.error("indent mobileDetail error:", e);
		}
		return "success";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<Map<String,Object>> u8list(Page<Map<String, Object>> page, String conditionStr, HttpServletResponse response) {
		logger.debug("u8list ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> result = new HashMap<>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = u8InterfaceService.u8FinanceAbnormallist(page);
		} catch (Exception e) {
			logger.error("u8list error:", e);
		}
		return page;
	}
}
