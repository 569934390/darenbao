package com.club.web.stock.controller;

import java.util.HashMap;
import java.util.List;
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
import com.club.web.common.Constants;
import com.club.web.stock.service.ClassifyColumnService;
import com.club.web.stock.vo.ClassifyColumnVo;

@Controller
@RequestMapping("/stock/classifyColumn")
public class ClassifyColumnController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClassifyColumnService classifyColumnService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page, String conditionStr) {
		logger.debug("classifyColumn list ");
		Map<String,Object> result = new HashMap<>();
		result.put(Constants.RESULT_CODE, 1);
		try {
			if (StringUtils.isNotEmpty(conditionStr) && page != null) {
				page.setConditons(JsonUtil.toMap(conditionStr));
			}
			page = classifyColumnService.list(page);
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("classifyColumn list error:", e);
		}
		return page;
	}
	
	/**
	 * 手机端列表展示
	 * @return
	 */
	@RequestMapping("/mobile/list")
	@ResponseBody
	public List<ClassifyColumnVo> getMobileList(HttpServletResponse response) {
		logger.debug("classifyColumn getMobileList ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return classifyColumnService.list();
		} catch (Exception e) {
			logger.error("classifyColumn getMobileList error:", e);
		}
		return null;
	}
	
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> add(String modelJson, HttpServletResponse response) {
		logger.debug("classifyColumn add ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ClassifyColumnVo classifyColumn = JsonUtil.toBean(modelJson, ClassifyColumnVo.class);
			result = classifyColumnService.saveOrUpdate(classifyColumn);
		}catch(Exception e){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "操作失败！");
			logger.error("classifyColumn add error:", e);
		}
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String ids, HttpServletResponse response) {
		logger.debug("classifyColumn delete");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = classifyColumnService.delete(ids);
		}catch(Exception e){
			result.put(Constants.RESULT_CODE, 0);
			result.put(Constants.RESULT_MSG, "操作失败！");
			logger.error("classifyColumn delete error:", e);
		}
		return result;
	}
}
