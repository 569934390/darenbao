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
import com.club.web.stock.dao.base.po.CargoSupplier;
import com.club.web.stock.service.CargoSupplierService;
import com.club.web.stock.vo.CargoSupplierVo;

/**
 * @Title: CargoSupplierController.java
 * @Package com.club.web.stock.controller
 * @Description: 供应商信息管理
 * @author 柳伟军
 * @date 2016年3月26日 上午9:27:21
 * @version V1.0
 */
@Controller
@RequestMapping("/cargoSupplier")
public class CargoSupplierController {

	private Logger logger = LoggerFactory.getLogger(CargoSupplierController.class);

	@Autowired
	private CargoSupplierService cargoSupplierService;

	/**
	 * 新增修改供应商
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdateCargoSupplier")
	@ResponseBody
	public Map<String, Object> saveOrUpdateCargoSupplier(@RequestParam(value = "modelJson",required = true)String modelJson, HttpServletRequest request) {
		logger.debug("saveOrUpdateCargoSupplier ");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (modelJson != null&&!"".equals(modelJson)) {
				CargoSupplierVo cargoSupplierVo = JsonUtil.toBean(modelJson, CargoSupplierVo.class);
				if (cargoSupplierVo == null) {
					result.put("success", false);
					result.put("msg", "供应商信息不能为空！");
				} else {
					result.putAll(cargoSupplierService.saveOrUpdateCargoSupplier(cargoSupplierVo, request));
				}
			} else {
				result.put("success", false);
				result.put("msg", "供应商信息为空，请确认modelJson参数不为空，且为json格式");
			}
		} catch (Exception e) {
			logger.error("编辑供应商异常<saveOrUpdateCargoSupplierCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
		}
		return result;
	}

	/**
	 * 删除供应商
	 * 
	 * @return
	 */
	@RequestMapping("/deleteCargoSupplier")
	@ResponseBody
	public Map<String, Object> deleteCargoSupplier(@RequestParam(value = "IdStr", required = true) String IdStr) {
		logger.debug("deletecargoSupplier ");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(cargoSupplierService.deleteCargoSupplier(IdStr));
		} catch (Exception e) {
			logger.error("删除供应商异常<deleteCargoSupplierCon>:", e);
			result.put("success", false);
			result.put("msg", "操作失败！");
			
		    if (e instanceof DataIntegrityViolationException) {
		    	result.put("msg", "删除选择项已经被引用，不能删除！");
	        }
		}
		return result;
	}

	/**
	 * 根据名称分页查询供应商信息
	 * 
	 * @param page
	 *            前台的分页信息
	 * @param conditionStr
	 *            查询条件（json格式）
	 * @return
	 */
	@RequestMapping("/cargoSupplierPage")
	@ResponseBody
	public Page<Map<String, Object>> cargoSupplierPage(Page<Map<String, Object>> page, String conditionStr,
			HttpServletResponse response) {

		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		// 根据查询分页信息和查询查询分页结果
		page = cargoSupplierService.queryCargoSupplierPage(page);
		return page;

	}

	/**
	 * 查询所有供应商信息
	 * 
	 * @return
	 */
	@RequestMapping("/findListAll")
	@ResponseBody
	public List<CargoSupplierVo> findListAll() {
		List<CargoSupplierVo> list = null;
		// 根据查询分页信息和查询查询分页结果
		try {
			list = cargoSupplierService.findListAll();
		} catch (Exception e) {
			logger.error("查询所有供应商异常<findListAllCargoSupplierCon>:", e);
		}

		return list;
	}

}
