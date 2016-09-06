
package com.club.web.event.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.event.service.OnlineStudyService;
import com.club.web.event.vo.EventOnlineStudyVo;

/**
 * @Title: OnlineStudyController.java
 * @Package com.club.web.event.controller
 * @Description: TODO(在线学习)
 * @author 柳伟军
 * @date 2016年4月13日 下午2:33:47
 * @version V1.0
 */
@Controller
@RequestMapping("/event/onlineStudy")
public class OnlineStudyController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OnlineStudyService onlineStudyService;

	/**
	 * 新增修改在线学习信息
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdateOnlineStudy")
	@ResponseBody
	public Map<String, Object> saveOrUpdateOnlineStudy(
			@RequestParam(value = "modelJson", required = true) String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateOnlineStudy ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventOnlineStudyVo eventOnlineStudyVo = JsonUtil.toBean(modelJson, EventOnlineStudyVo.class);
				if (eventOnlineStudyVo == null) {
					result.put("success", false);
					result.put("msg", "学习信息不能为空！");
				} else {
					result.putAll(onlineStudyService.saveOrUpdateOnlineStudy(eventOnlineStudyVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "学习信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑学习异常<saveOrUpdateOnlineStudyCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 根据名称分页查询在线学习信息
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/onlineStudyPage")
	@ResponseBody
	public Page<Map<String, Object>> onlineStudyPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = onlineStudyService.queryOnlineStudyPage(page);
//		System.out.println(JsonUtil.toJson(page));
		return page;
	}

	/**
	 * 删除学习信息
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("delete ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(onlineStudyService.delete(IdStr));
		} catch (Exception e) {
			logger.error("删除学习信息异常<delete OnlineStudyController>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");

			if (e instanceof DataIntegrityViolationException) {
				result.put("msg", "删除选择项已经被引用，不能删除！");
			}
		}
		return result;
	}
	
	
	/**
	 *手机端分页
	 */
	@RequestMapping("/mobile/findAllForMobile")
	@ResponseBody
	public Page<Map<String, Object>> findAllForMobile(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("findAllForMobile ");
		
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = onlineStudyService.queryOnlineStudyPage(page);
		return page;
	}
	/**
	 *手机端根据ID查询详情
	 * @return
	 */
	@RequestMapping("/mobile/findOneById")
	@ResponseBody
	public Map<String,Object> findOneById(String id,
			HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		logger.debug("findOneById ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(onlineStudyService.findOneById(id));
		} catch (Exception e) {
			logger.error("c查询学习信息异常<findOneById findOneByIdController>:", e);
			result.put("success", false);
			result.put("msg", "系统出错！");
		}
		return result;
	}

}
