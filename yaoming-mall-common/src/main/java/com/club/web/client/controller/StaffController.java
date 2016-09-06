package com.club.web.client.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.StaffService;

@Controller
@RequestMapping("/staffManager")
public class StaffController {

	private Logger logger = LoggerFactory.getLogger(StaffController.class);

	@Autowired private StaffService staffService;

	@RequestMapping("/staffPage")
	@ResponseBody
	public Page<Map<String,Object>> staffPage(Page<Map<String,Object>> page,String conditionStr) throws BaseAppException {
		logger.debug("staffPage ");
		if (conditionStr!=null)
			page.setConditons(JsonUtil.toMap(conditionStr));
		Map<String, Object> conditions = page.getConditons();
		conditions.put("start", page.getStart());
		conditions.put("limit", page.getLimit());
		return staffService.selectPage(conditions);
	}

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(@RequestParam("params") String params) {
		logger.debug("save staff. ");
		Map<String, Object> result = new HashMap<>();
		try {
			Map<String, Object> paramsMap = new HashMap<>();
			if (params!=null)
				paramsMap.putAll(JsonUtil.toMap(params));
	        
	        staffService.save(paramsMap);
	        
			result.put("code", 1);
			result.put("msg", "保存成功");
		} catch (Exception e) {
			logger.error("", e);
			result.put("code", 0);
			result.put("msg", "保存失败");
		}
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("params") String params) {
		logger.debug("save staff. ");
		Map<String, Object> result = new HashMap<>();
		try {
			String[] ids = new String[0];
			if (StringUtils.isNotEmpty(params))
				ids = params.split(",");
			staffService.delete(ids);
			result.put("code", 1);
			result.put("msg", "删除成功");
		} catch (Exception e) {
			logger.error("", e);
			result.put("code", 0);
			result.put("msg", "删除失败");
		}
		return result;
	}
}
