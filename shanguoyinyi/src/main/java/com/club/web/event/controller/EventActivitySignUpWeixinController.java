package com.club.web.event.controller;

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

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.event.service.EventActivitySignUpService;
import com.club.web.event.vo.EventActivitySaveVo;
import com.club.web.event.vo.EventActivitySignUpSaveVo;
import com.club.web.event.vo.EventActivityUserinfoVo;

/**
* @Title: EventActivityUserController.java 
* @Package com.club.web.event.controller 
* @Description: TODO(微信端活动报名信息) 
* @author 柳伟军   
* @date 2016年4月20日 下午2:23:22 
* @version V1.0
 */
@Controller
@RequestMapping("/event/eventActivitySignUp")
public class EventActivitySignUpWeixinController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	EventActivitySignUpService activityUserService;
	
	/**
	 * 根据名称活动报名信息
	 * @param page前台的分页信息
	 * @param conditionStr 查询条件（json格式）
	 */
	@RequestMapping("/weixin/eventActivityUserPage")
	@ResponseBody
	public Page<Map<String, Object>> eventActivityUserPageForMobile(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = activityUserService.queryActivityUserPage(page);
		return page;
	}
	
	/**
	 * 微信端活动报名
	 * @return
	 */
	@RequestMapping("/weixin/signUp")
	@ResponseBody
	public Map<String,Object> signUp(String modelJson,HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("weixin/signUp ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventActivitySignUpSaveVo activitySignUpVo = JsonUtil.toBean(modelJson, EventActivitySignUpSaveVo.class);
				if (activitySignUpVo == null) {
					result.put("success", false);
					result.put("msg", "报名信息不能为空！");
				} else {
					result.putAll(activityUserService.signUp(activitySignUpVo,request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "报名信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("新增报名信息异常<signUp EventActivitySignUpController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
	
}
