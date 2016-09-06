package com.club.web.module.controller;

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

import com.club.framework.util.JsonUtil;
import com.club.web.module.constant.CommonTextType;
import com.club.web.module.service.CommonTextService;
import com.club.web.module.vo.CommonTextVo;
/**
 * 通用文本控制类
 * @author zhuzd
 *
 */
@Controller
@RequestMapping("/module/text")
public class CommonTextController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonTextService commonTextService;
	
	@RequestMapping("/{type}")
	@ResponseBody
	public CommonTextVo getTextVoByType(@PathVariable String type,HttpServletResponse response){
		logger.debug("CommonText getTextVoByType");
		try{
			return commonTextService.findTextVoByType(CommonTextType.getDbDataByName(type));
		}catch(Exception e){
			logger.error("CommonText getTextVoByType error:"+e);
		}
		return null;
	}
	
	@RequestMapping("/mobile/{type}")
	@ResponseBody
	public CommonTextVo getMobileTextVoByType(@PathVariable String type,HttpServletResponse response){
		logger.debug("CommonText getMobileTextVoByType");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try{
			return commonTextService.findTextVoByType(CommonTextType.getDbDataByName(type));
		}catch(Exception e){
			logger.error("CommonText getMobileTextVoByType error:"+e);
		}
		return null;
	}
	
	@RequestMapping("/addOrModify")
	@ResponseBody
	public Map<String,Object> addOrModify(String modelJson){
		logger.debug("CommonText addOrModify");
		Map<String,Object> map = new HashMap<>();
		map.put("code", 0);
		map.put("msg", "操作失败！");
		try{
			CommonTextVo commonTextVo = JsonUtil.toBean(modelJson, CommonTextVo.class);
			if(commonTextService.addOrModify(commonTextVo)){
				map.put("code", 1);
				map.put("msg", commonTextVo);
			}
		}catch(Exception e){
			map.put("code", 0);
			map.put("msg", "操作异常！");
			logger.error("CommonText addOrModify error:"+e);
		}
		return map;
	}
}
