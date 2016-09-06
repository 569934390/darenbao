package com.club.web.event.controller;

import java.util.HashMap;
import java.util.Map;

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

/**
* @Title: EventActivityUserController.java 
* @Package com.club.web.event.controller 
* @Description: TODO(活动报名信息) 
* @author 柳伟军   
* @date 2016年4月20日 下午2:23:22 
* @version V1.0
 */
@Controller
@RequestMapping("/event/eventActivitySignUp")
public class EventActivitySignUpController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	EventActivitySignUpService activityUserService;
	
	/**
	 * 根据名称活动报名信息
	 * @param page前台的分页信息
	 * @param conditionStr 查询条件（json格式）
	 */
	@RequestMapping("/eventActivityUserPage")
	@ResponseBody
	public Page<Map<String, Object>> eventActivityUserPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = activityUserService.queryActivityUserPage(page);
		return page;
	}
	
	/**
	 * 导出报名名单
	 * @param conditionStr
	 * @param response
	 * @return
	 */
	@RequestMapping("/userExportData")
	@ResponseBody
	public String userExportData(String conditionStr,
			HttpServletResponse response) {
			try {
				Map map=JsonUtil.toMap(conditionStr);
				activityUserService.userExportData(map,response);
			} catch (Exception e) {
				logger.error("EventActivitySignUpController userExportData error ",e);
			}
		return null;
	}
}
