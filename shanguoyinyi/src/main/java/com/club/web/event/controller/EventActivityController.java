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
 * @Description: TODO(活动部落信息)
 * @author 柳伟军
 * @date 2016年4月19日 上午10:35:46
 * @version V1.0
 */
@Controller
@RequestMapping("/event/eventActivity")
public class EventActivityController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivityService eventActivityService;
	
	/**
	 * 根据groupId获取图片
	 * 
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/getImage")
	@ResponseBody
	public ActivityImageGroupVo getImage(long groupId) {
		return eventActivityService.getImageGroup(groupId);
	}

	/**
	 * 根据ID获取活动部落
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findEventActivityById")
	@ResponseBody
	public EventActivityDetailsVo findEventActivityById(String id) {
		return eventActivityService.findEventActivityById(id);
	}

	/**
	 * 新增编辑
	 */
	@RequestMapping("/saveOrUpdateEventActivity")
	@ResponseBody
	public Map<String, Object> saveOrUpdateEventActivity(String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateEventActivity ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventActivitySaveVo activitySaveVo = JsonUtil.toBean(modelJson, EventActivitySaveVo.class);
				if (activitySaveVo == null) {
					result.put("success", false);
					result.put("msg", "活动信息不能为空！");
				} else {
					result.putAll(eventActivityService.saveOrUpdateEventActivity(activitySaveVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "活动信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("新增编辑异常<saveOrUpdateEventActivity EventActivityController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 根据名称分页查询活动部落信息
	 * 
	 * @param page前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 */
	@RequestMapping("/eventActivityPage")
	@ResponseBody
	public Page<Map<String, Object>> eventActivityPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = eventActivityService.queryEventActivityPage(page);
		return page;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "Ids", required = true) String Ids) {
		logger.debug("delete ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(eventActivityService.delete(Ids));
		} catch (Exception e) {
			logger.error("删除异常<delete EventActivityController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");

			if (e instanceof DataIntegrityViolationException) {
				result.put("msg", "删除选择项已经被引用，不能删除！");
			}
		}
		return result;
	}

	/**
	 * 审核通过
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Map<String, Object> check(@RequestParam(value = "Ids", required = true) String Ids,
			HttpServletRequest request) {
		logger.debug("check ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(eventActivityService.check(Ids, request));
		} catch (Exception e) {
			logger.error("审核通过异常<check EventActivityController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 审核通过
	 */
	@RequestMapping("/noCheck")
	@ResponseBody
	public Map<String, Object> noCheck(@RequestParam(value = "Ids", required = true) String Ids, String failure,
			HttpServletRequest request) {
		logger.debug("noCheck ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(eventActivityService.noCheck(Ids, failure, request));
		} catch (Exception e) {
			logger.error("审核不通过异常<noCheck EventActivityController>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
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
			@RequestParam(value = "Ids", required = true) String Ids, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(eventActivityService.updateStatus(action, Ids, request));
		} catch (Exception e) {
			logger.error("更新活动部落状态 " + action + " 异常<action>:{}", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

}
