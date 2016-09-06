package com.club.web.module.controller;

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
import com.club.framework.util.StringUtils;
import com.club.web.module.service.OpinionService;
import com.club.web.module.vo.OpinionVo;
/**
 * 意见反馈控制类
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/module/opinion")
public class OpinionController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OpinionService opinionService;
	
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page, String conditionStr,HttpServletResponse response) {
		logger.debug("opinion list ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = opinionService.list(page);
		} catch (Exception e) {
			logger.error("opinion list error:", e);
		}
		return page;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(String modelJson,HttpServletResponse response){
		logger.debug("opinion add");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			OpinionVo opinionVo = JsonUtil.toBean(modelJson, OpinionVo.class);
			if(!opinionService.add(opinionVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作异常！");
			logger.error("opinion add error:",e);
		}
		return result;
	}	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(String ids,HttpServletResponse response){
		logger.debug("opinion delete");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			if(StringUtils.isNotEmpty(ids) && !opinionService.deleteByIds(ids)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作异常！");
			logger.error("opinion add error:",e);
		}
		return result;
	}	
	
	@RequestMapping("/mobile/add")
	@ResponseBody
	public Map<String,Object> mobileAdd(String modelJson,HttpServletResponse response){
		logger.debug("opinion mobileAdd");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			OpinionVo opinionVo = JsonUtil.toBean(modelJson, OpinionVo.class);
			if(!opinionService.add(opinionVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("opinion mobileAdd error:",e);
		}
		return result;
	}
	
	@RequestMapping("/mobile/weixin/add")
	@ResponseBody
	public Map<String,Object> weixinAdd(String modelJson,HttpServletResponse response){
		logger.debug("opinion weixinAdd");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("code",1);
		result.put("msg","操作成功！");
		try {
			OpinionVo opinionVo = JsonUtil.toBean(modelJson, OpinionVo.class);
			if(!opinionService.weixinAdd(opinionVo)){
				result.put("code",0);
				result.put("msg","操作失败！");
			}
		} catch (Exception e) {
			result.put("code",0);
			result.put("msg","操作失败！"+e.getMessage());
			logger.error("opinion weixinAdd error:",e);
		}
		return result;
	}
}
