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
import com.club.web.event.service.EventActivityPointService;
import com.club.web.event.vo.EventActivityPointSaveVo;

/**
* @Title: EventActivityPointController.java 
* @Package com.club.web.event.controller 
* @Description: TODO(活动点赞) 
* @author 柳伟军   
* @date 2016年4月28日 下午2:57:59 
* @version V1.0
 */
@Controller
@RequestMapping("/event/eventActivityPoint")
public class EventActivityPointWeixinController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired EventActivityPointService activityPointService;
	
	/**
	 * 微信端活动点赞
	 * @return
	 */
	@RequestMapping("/weixin/point")
	@ResponseBody
	public Map<String,Object> point(String modelJson,HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("weixin/point ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventActivityPointSaveVo activityPointVo = JsonUtil.toBean(modelJson, EventActivityPointSaveVo.class);
				if (activityPointVo == null) {
					result.put("success", false);
					result.put("msg", "点赞信息不能为空！");
				} else {
					result.putAll(activityPointService.point(activityPointVo,request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "点赞信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("新增点赞信息异常<Point EventActivityPointWeixinController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
	
	/**
	 * 微信端活动取消点赞
	 * @return
	 */
	@RequestMapping("/weixin/cancelPoint")
	@ResponseBody
	public Map<String,Object> cancelPoint(String modelJson,HttpServletRequest request,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("weixin/point ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventActivityPointSaveVo activityPointVo = JsonUtil.toBean(modelJson, EventActivityPointSaveVo.class);
				if (activityPointVo == null) {
					result.put("success", false);
					result.put("msg", "取消点赞信息不能为空！");
				} else {
					result.putAll(activityPointService.cancelPoint(activityPointVo,request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "取消点赞信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("取消点赞信息异常<cancelPoint EventActivityPointWeixinController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
}
