package com.club.web.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.club.web.store.service.StoreLevelService;
import com.club.web.store.vo.StoreLevelVo;
/**
 * @Title: StoreLevelController.java
 * @Package com.club.web.store.controller
 * @Description: 店铺等级管理
 * @author 柳伟军
 * @date 2016年3月26日 上午10:43:59
 * @version V1.0
 */
@Controller
@RequestMapping("/store/level")
public class StoreLevelController {

	private Logger logger = LoggerFactory.getLogger(StoreLevelController.class);

	@Autowired
	private StoreLevelService storeLevelService;

	/**
	 * 新增修改店铺等级信息
	 * @param modelJson 前台传入值(json格式)
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrUpdateStoreLevel")
	@ResponseBody
	public Map<String, Object> saveOrUpdateStoreLevel(@RequestParam(value = "modelJson",required = true)String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateStoreLevel ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null&&!"".equals(modelJson)) {
				StoreLevelVo storeLevelVo = JsonUtil.toBean(modelJson, StoreLevelVo.class);
				if (storeLevelVo == null) {
					result.put("success", false);
					result.put("msg", "店铺等级信息不能为空！");
				} else {
			
					result.putAll(storeLevelService.saveOrUpdateStoreLevel(storeLevelVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "店铺等级为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑店铺等级异常<saveOrUpdateStoreLevel>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
	
	/**
	 * 根据名称分页查询店铺等级信息
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/storeLevelPage")
	@ResponseBody
	public Page<Map<String, Object>> storeLevelPage(Page<Map<String, Object>> page, HttpServletRequest request) {

		// 根据查询分页信息和查询查询分页结果
		page = storeLevelService.queryStoreLevelPage(page,request);
		return page;
	}

	
	/**
	 * 删除店铺等级信息
	 */
	@RequestMapping("/deleteStoreLevel")
	@ResponseBody
	public Map<String, Object> deleteStoreLevel(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("deleteStoreLevel ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(storeLevelService.deleteStoreLevel(IdStr));
		} catch (Exception e) {
			logger.error("删除店铺等级异常<deleteStoreLevelCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
			
		    if (e instanceof DataIntegrityViolationException) {
		    	result.put("msg", "删除选项存在已经被引用，不能删除！");
	        }
		}
		return result;
	}
	
	/**
	 * 更新店铺等级状态
	 */
	@RequestMapping("/updateStoreLevelStatue")
	@ResponseBody
	public Map<String, Object> updateStoreLevelStatue(@RequestParam(value = "IdStr", required = true) String IdStr,
			@RequestParam(value = "statue", required = true) Long statue, HttpServletRequest request) {
		logger.debug("updateStoreLevelStatue ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(storeLevelService.updateStoreLevelStatue(IdStr, statue, request));
		} catch (Exception e) {
			logger.error("更新店铺等级状态异常<updateStoreLevelStatue>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}
	
	/**
	 * 获取当前用户绑定总店有效的店铺等级
	 */
	@RequestMapping("/findAllStoreLevel")
	@ResponseBody
	public List<StoreLevelVo> findAllStoreLevel(HttpServletRequest request) {
		List<StoreLevelVo> levelVoList=null;
		try {
			levelVoList=storeLevelService.findAllStoreLevel(request);
		} catch (Exception e) {
			logger.error("获取当前用户绑定总店有效的店铺等级<findAllStoreLevel>:", e);
		}
		return levelVoList;
	}
}
