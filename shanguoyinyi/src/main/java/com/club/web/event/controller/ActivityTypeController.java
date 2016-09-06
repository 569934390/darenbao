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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.event.service.ActivityTypeService;
import com.club.web.event.vo.EventActivityTypeVo;

/**
* @Title: ActivityTypeController.java 
* @Package com.club.web.event.controller 
* @Description: TODO(活动分类) 
* @author 柳伟军   
* @date 2016年4月18日 下午2:40:23 
* @version V1.0
 */
@Controller
@RequestMapping("/event/activityType")
public class ActivityTypeController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ActivityTypeService activityTypeService;
	
	/**
	 * 手机端活动部落
	 * @return
	 */
	@RequestMapping("/mobile/findEventActivityTypeListForMobile")
	@ResponseBody
	public List<EventActivityTypeVo> findEventActivityTypeListForMobile(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<EventActivityTypeVo> activityMobileVoList=null;
		try {
			activityMobileVoList=activityTypeService.findList();
		} catch (Exception e) {
			logger.error("ActivityTypeController findEventActivityTypeListForMobile error :{}", e.getMessage());
		}
		return activityMobileVoList;
	}
	
	/**
	 * 微信端活动部落
	 * @return
	 */
	@RequestMapping("/weixin/findEventActivityTypeListForWeixin")
	@ResponseBody
	public List<EventActivityTypeVo> findEventActivityTypeListForWeixin(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<EventActivityTypeVo> activityMobileVoList=null;
		try {
			activityMobileVoList=activityTypeService.findList();
		} catch (Exception e) {
			logger.error("ActivityTypeController findEventActivityTypeListForWeixin error :{}", e.getMessage());
		}
		return activityMobileVoList;
	}
	
	/**
	 * 新增修改活动分类信息
	 * @return
	 */
	@RequestMapping("/saveOrUpdateActivityType")
	@ResponseBody
	public Map<String, Object> saveOrUpdateActivityType(
			@RequestParam(value = "modelJson", required = true) String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateActivityType ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null && !"".equals(modelJson)) {
				EventActivityTypeVo eventActivityTypeVo = JsonUtil.toBean(modelJson, EventActivityTypeVo.class);
				if (eventActivityTypeVo == null) {
					result.put("success", false);
					result.put("msg", "活动分类不能为空！");
				} else {
					result.putAll(activityTypeService.saveOrUpdateActivityType(eventActivityTypeVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "活动分类为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑活动分类异常<saveOrUpdateActivityTypeCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
	

	/**
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/activityTypePage")
	@ResponseBody
	public Page<Map<String, Object>> activityTypePage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = activityTypeService.queryActivityTypePage(page);
		return page;
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("delete ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(activityTypeService.delete(IdStr));
		} catch (Exception e) {
			logger.error("删除活动分类异常<delete ActivityTypeController>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");

			if (e instanceof DataIntegrityViolationException) {
				result.put("msg", "删除选择项已经被引用，不能删除！");
			}
		}
		return result;
	}
	/**
	 * 启用禁用
	 */
	@RequestMapping("/updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus(@RequestParam(value = "IdStr", required = true) String IdStr,long status, HttpServletRequest request) {
		logger.debug("delete ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(activityTypeService.updateStatus(IdStr,status, request));
		} catch (Exception e) {
			logger.error("修改活动分类状态异常<updateStatus ActivityTypeController>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
	/**
	 * 查询分类列表
	 */
	@RequestMapping("/findList")
	@ResponseBody
	public List<EventActivityTypeVo> findList(){
		try {
			return activityTypeService.findList();
		} catch (Exception e) {
			logger.error("ActivityTypeController findList error : {}", e.getMessage());
			return null;
		}
	}

}
