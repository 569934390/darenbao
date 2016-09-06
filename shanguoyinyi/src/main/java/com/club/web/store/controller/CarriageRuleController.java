package com.club.web.store.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.club.web.store.service.CarriageRuleService;
import com.club.web.store.vo.CarriageRuleDetailVo;
import com.club.web.store.vo.CarriageRuleVo;
import com.yaoming.common.util.StringUtil;

/**   
* @Title: CarriageRuleController.java
* @Package com.club.web.store.controller 
* @Description: 包邮规则 
* @author hqLin
* @date 2016/07/19
* @version V1.0
*/

@Controller
@RequestMapping("/carriageRuleController")
public class CarriageRuleController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CarriageRuleService carriageRuleService;
	
	/**
	 * 查询运费模板
	 * @param page 分页信息
	 * @return
	 */
	@RequestMapping("/selectCarriageRule")
	@ResponseBody
	public Page<Map<String, Object>> selectCarriageRule(Page<Map<String, Object>> page) {
		page = carriageRuleService.selectCarriageRule(page);	
		
		return page;
	}
	
	/**
	 * 新增或更新运费模板
	 * @param carriageRuleId
	 * @param templateName
	 * @param carriage
	 * @param CarriageRuleDetailList
	 * @return
	 */
	@RequestMapping("/addOrUpdCarriageRule")
	@ResponseBody
	public Map<String, Object> addOrUpdCarriageRule(String carriageRuleId,String templateName, BigDecimal carriage, 
			String carriageRuleDetail) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CarriageRuleDetailVo> list = new ArrayList<CarriageRuleDetailVo>();
		list = JsonUtil.toList(carriageRuleDetail, CarriageRuleDetailVo.class);
		try {
			result.putAll(carriageRuleService.addOrUpdCarriageRule(carriageRuleId,templateName, carriage, list));
		} catch (Exception e) {
			logger.error("新增或更新运费模板异常:", e);
			if(carriageRuleId != null && StringUtil.isNotEmpty(carriageRuleId)){
				result.put("code", false);
				result.put("msg", "修改失败");
			} else{
				result.put("code", false);
				result.put("msg", "新增失败");
			}
		}
		
		return result;
	}
	
	@RequestMapping("/queryCarriageRuleDetail")
	@ResponseBody
	public Map<String, Object> queryCarriageRuleDetail(String carriageRuleId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(carriageRuleId == null || carriageRuleId.isEmpty()){
			result.put("code", false);
			result.put("msg", "模板ID不能为空");
		} else{
			result.putAll(carriageRuleService.queryCarriageRuleDetail(carriageRuleId));
		}
		
		return result;
	}
	
	@RequestMapping("/deleteCarriageRule")
	@ResponseBody
	public Map<String, Object> deleteCarriageRule(String carriageRuleId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(carriageRuleService.deleteCarriageRule(carriageRuleId));
		} catch (Exception e) {
			result.put("code", false);
			result.put("msg", "删除失败");
		}
		
		return result;
	}
	
	@RequestMapping("/getCarriageRuleList")
	@ResponseBody
	public List<CarriageRuleVo> getCarriageRuleList() {
		List<CarriageRuleVo> result = new ArrayList<CarriageRuleVo>();
		try {
			result = carriageRuleService.getCarriageRuleList();
		} catch (Exception e) {
			logger.error("查询包邮规则异常:", e);
		}
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCarriageByRegionId")
	@ResponseBody
	public BigDecimal getCarriageByRegionId(String indentInfo, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		BigDecimal carriage = BigDecimal.ZERO;
		List<Map> list = JsonUtil.toList(indentInfo, Map.class);
		carriage = carriageRuleService.getCarriageByRegionId(list);
		
		return carriage;
	}
}