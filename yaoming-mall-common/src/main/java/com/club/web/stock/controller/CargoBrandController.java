package com.club.web.stock.controller;

import java.util.ArrayList;
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
import com.club.web.stock.service.CargoBrandService;
import com.club.web.stock.vo.CargoBrandVo;

/**
 * @Title: CargoBrandController.java
 * @Package com.club.web.stock.controller
 * @Description: 品牌信息管理
 * @author 柳伟军
 * @date 2016年3月26日 上午9:27:39
 * @version V1.0
 */
@Controller
@RequestMapping("/cargoBrand")
public class CargoBrandController {

	private Logger logger = LoggerFactory.getLogger(CargoBrandController.class);

	@Autowired
	private CargoBrandService cargoBrandService;

	/**
	 * 新增修改品牌
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdateCargoBrand")
	@ResponseBody
	public Map<String, Object> saveOrUpdateCargoBrand(@RequestParam(value = "modelJson",required = true)String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateCargoBrand ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null&&!"".equals(modelJson)) {
				CargoBrandVo cargoBrandVo = JsonUtil.toBean(modelJson, CargoBrandVo.class);
				if (cargoBrandVo == null) {
					result.put("success", false);
					result.put("msg", "品牌信息不能为空！");
				} else {
					result.putAll(cargoBrandService.saveOrUpdateCargoBrand(cargoBrandVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "品牌信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑品牌异常<saveOrUpdateCargoBrandCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 删除品牌
	 * 
	 * @return
	 */
	@RequestMapping("/deleteCargoBrand")
	@ResponseBody
	public Map<String, Object> deleteCargoBrand(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("deleteCargoBrand ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(cargoBrandService.deleteCargoBrand(IdStr));
		} catch (Exception e) {
			logger.error("删除品牌异常<deleteCargoBrandCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		
			if (e instanceof DataIntegrityViolationException) {
			    result.put("msg", "删除选择项已经被引用，不能删除！");
		    }
		}
		return result;
	}

	/**
	 * 根据名称分页查询品牌信息
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/cargoBrandPage")
	@ResponseBody
	public Page<Map<String, Object>> cargoBrandPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = cargoBrandService.queryCargoBrandPage(page);
		return page;

	}

	/**
	 * 查询所有品牌信息
	 * 
	 * @return
	 */
	@RequestMapping("/findListAll")
	@ResponseBody
	public List<CargoBrandVo> findListAll() {
		List<CargoBrandVo> list = null;
		try {
			// 根据查询分页信息和查询查询分页结果
			list = cargoBrandService.findListAll();
		} catch (Exception e) {
			logger.error("查询所有品牌异常<findListAllCargoBrandCon>:", e);
		}

		return list;
	}

}
