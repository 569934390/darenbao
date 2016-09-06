package com.club.web.event.controller;

import java.sql.SQLException;
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
import com.club.web.event.service.OnlineStudyTypeService;
import com.club.web.event.vo.EventOnlineStudyTypeVo;
import com.club.web.store.vo.TradeHeadStoreVo;

/**
 * @Title: OnlineStudyTypeController.java
 * @Package com.club.web.event.controller
 * @Description: TODO(在线学习分类)
 * @author 柳伟军
 * @date 2016年4月9日 上午11:59:31
 * @version V1.0
 */
@Controller
@RequestMapping("/event/onlineStudyType")
public class OnlineStudyTypeController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OnlineStudyTypeService onlineStudyTypeService;

	/**
	 * 新增修改总店信息
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdateOnlineStudyType")
	@ResponseBody
	public Map<String, Object> saveOrUpdateOnlineStudyType(
			@RequestParam(value = "modelJson", required = true) String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateOnlineStudyType ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventOnlineStudyTypeVo eventOnlineStudyTypeVo = JsonUtil.toBean(modelJson,
						EventOnlineStudyTypeVo.class);
				if (eventOnlineStudyTypeVo == null) {
					result.put("success", false);
					result.put("msg", "学习分类信息不能为空！");
				} else {
					result.putAll(onlineStudyTypeService.saveOrUpdateOnlineStudyType(eventOnlineStudyTypeVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "学习分类信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑学习分类异常<saveOrUpdateOnlineStudyTypeCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 获取第一级列表
	 * 
	 * @return
	 */
	@RequestMapping("/getParents")
	@ResponseBody
	public Page<EventOnlineStudyTypeVo> getParents() {
		try {
			return onlineStudyTypeService.getParents();
		} catch (Exception e) {
			logger.error("OnlineStudyTypeController getParents error:", e);
		}
		return null;
	}

	/**
	 * 获取第二级列表
	 * 
	 * @return
	 */
	@RequestMapping("/getByParentId")
	@ResponseBody
	public List<EventOnlineStudyTypeVo> getByParentId(String parentId) {
		try {
			return onlineStudyTypeService.getVoByParentId(parentId);
		} catch (Exception e) {
			logger.error("OnlineStudyTypeController getByParentId error:", e);
		}
		return null;
	}
	
	/**
	 * 学习信息获取第一级列表
	 * 
	 * @return
	 */
	@RequestMapping("/getParentList")
	@ResponseBody
	public List<EventOnlineStudyTypeVo> getParentList() {
		try {
			return onlineStudyTypeService.getParentList();
		} catch (Exception e) {
			logger.error("OnlineStudyTypeController getParentList error:", e);
		}
		return null;
	}
	
	/**
	 * 学习信息获取第二级列表
	 * 
	 * @return
	 */
	@RequestMapping("/getListByParentId")
	@ResponseBody
	public List<EventOnlineStudyTypeVo> getListByParentId(String parentId) {
		try {
			return onlineStudyTypeService.getListByParentId(parentId);
		} catch (Exception e) {
			logger.error("OnlineStudyTypeController getByListParentId error:", e);
		}
		return null;
	}

	/**
	 * 启用禁用
	 * 
	 * @param action
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/{action}")
	@ResponseBody
	public Map<String, Object> action(@PathVariable String action,
			@RequestParam(value = "ids", required = true) String ids, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(onlineStudyTypeService.updateStatus(action, ids, request));
		} catch (Exception e) {
			logger.error("更新学习分类状态 " + action + " 异常<action>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param action
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "ids", required = true) String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(onlineStudyTypeService.delete(ids));
		} catch (Exception e) {
			logger.error("删除学习分类状态异常<delete>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
			
		    if (e instanceof DataIntegrityViolationException) {
		    	result.put("msg", "删除选择项已经被引用或者孩子节点已被引用，不能删除！");
	        }
		}
		return result;
	}
	
	/**
	 * 手机端学习信息获取第一级列表
	 * 
	 * @return
	 */
	@RequestMapping("/mobile/getMobileParentList")
	@ResponseBody
	public List<EventOnlineStudyTypeVo> getMobileParentList(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return onlineStudyTypeService.getParentList();
		} catch (Exception e) {
			logger.error("OnlineStudyTypeController getMobileParentList error:", e);
		}
		return null;
	}
	
	/**
	 * 手机端学习信息获取第二级列表
	 * 
	 * @return
	 */
	@RequestMapping("/mobile/getMobileListByParentId")
	@ResponseBody
	public List<EventOnlineStudyTypeVo> getMobileListByParentId(String parentId,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return onlineStudyTypeService.getListByParentId(parentId);
		} catch (Exception e) {
			logger.error("OnlineStudyTypeController getMobileListByParentId error:", e);
		}
		return null;
	}

}
