package com.club.web.event.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.event.service.EventActivityService;
import com.club.web.event.vo.ActivityImageGroupVo;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.event.vo.EventActivityMobileVo;
import com.club.web.event.vo.EventActivitySaveVo;

/**
 * @Title: EventActivityController.java
 * @Package com.club.web.event.controller
 * @Description: TODO(微信端活动部落信息)
 * @author 柳伟军
 * @date 2016年4月19日 上午10:35:46
 * @version V1.0
 */
@Controller
@RequestMapping("/event/eventActivity")
public class EventActivityWeixinController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivityService eventActivityService;

	/**
	 * 微信端活动部落分页查询
	 * @return
	 */
	@RequestMapping("/weixin/findEventActivityForMobile")
	@ResponseBody
	public Page<Map<String, Object>> findEventActivityForMobile(Page<Map<String, Object>> page,String conditionStr,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("findEventActivityForMobile ");
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null&&!"".equals(conditionStr)) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = eventActivityService.queryEventActivityFromMobilePage(page);
		return page;
	}

	/**
	 * 微信端查询活动详情
	 * 
	 * @return
	 */
	@RequestMapping("/weixin/findEventActivityByIdForMobile")
	@ResponseBody
	public EventActivityMobileVo findEventActivityByIdForMobile(
			@RequestParam(value = "id", required = true) String id,String userId,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		EventActivityMobileVo eventActivityMobileVo = null;
		try {
			eventActivityMobileVo = eventActivityService.findEventActivityByIdForMobile(id,userId);
		} catch (Exception e) {
			logger.error("EventActivityController findEventActivityByIdForMobile error :{}", e.getMessage());
		}
		return eventActivityMobileVo;
	}
}
