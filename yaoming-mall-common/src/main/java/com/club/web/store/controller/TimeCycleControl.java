package com.club.web.store.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.web.store.constant.TimeCycleType;
import com.club.web.store.service.TimeCycleService;
import com.club.web.store.vo.TimeCycleVo;

@Controller
@RequestMapping("/timecycle")
public class TimeCycleControl {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TimeCycleService timeCycleService;
	
	@RequestMapping("/saveOrUpdate/{action}")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(@PathVariable String action,@RequestParam(value = "modelJson", required = true) String modelJson){
		logger.info("timecycle saveOrUpdate");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			TimeCycleVo timeCycle = JsonUtil.toBean(modelJson, TimeCycleVo.class);
			timeCycle.setType(TimeCycleType.getDbDataByName(action));
			result = timeCycleService.saveOrUpdate(timeCycle);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "数据更新失败！");
			logger.error("timecycle saveOrUpdate error:",e);
		}
		return result;
	}
	
	@RequestMapping("/detail/{action}")
	@ResponseBody
	public TimeCycleVo detail(@PathVariable String action){
		logger.info("timecycle detail");
		try {
			 return timeCycleService.detail(TimeCycleType.getDbDataByName(action));
		} catch (Exception e) {
			logger.error("timecycle detail error:",e);
		}
		return null;
	}
}
