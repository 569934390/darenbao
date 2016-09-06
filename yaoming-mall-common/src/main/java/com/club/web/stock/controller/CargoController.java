package com.club.web.stock.controller;

import java.net.URLDecoder;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.framework.util.Utils;
import com.club.web.common.Constants;
import com.club.web.stock.service.CargoService;
import com.club.web.stock.vo.CargoInfoVo;
import com.club.web.stock.vo.CargoSaveVo;
import com.club.web.stock.vo.CargoSimpleInfoVo;
import com.club.web.stock.vo.CargoSkuSimpleVo;

/**
 * Cargo Controller
 * @author yunpeng.xiong
 *
 */
@RequestMapping("/cargo")
@Controller
public class CargoController {

	private Logger logger = LoggerFactory.getLogger(CargoController.class);
	
	@Autowired private CargoService cargoService;

    @RequestMapping("index")
    public String index(Model model) {
        return "/test";
    }
    
	/**
	 * 保存货品，此处包括货品，货品规格，货品SKU
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(@RequestParam("cargo") String cargoJsonStr, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("msg", "保存成功");
		try {
			long creatorId = getUserId(request);
			cargoService.saveCargo(creatorId, JsonUtil.toBean(URLDecoder.decode(cargoJsonStr, "utf-8"), CargoSaveVo.class));
		} catch (Exception e) {
			if(e instanceof BatchUpdateException || e instanceof DuplicateKeyException) {
				if(e.getMessage().contains("cargo_sku"))
					result.put("msg", "货品保存失败，SKU编号已存在");
				else if(e.getMessage().contains("cargo"))
					result.put("msg", "货品保存失败，货品编号已存在");
			}else
				result.put("msg", e.getMessage());
			result.put("code", 0);
			logger.error("保存货品失败", e);
		}
		return result;
	}
	
	/**
	 * 分页查询货品信息
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public Page<CargoSimpleInfoVo> getList(Page<CargoSimpleInfoVo> page, String conditionStr) {
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null)
			page.setConditons(JsonUtil.toMap(conditionStr));
		// 根据查询分页信息和查询查询分页结果
		return cargoService.queryCargoList(page);

	}
	
	/**
	 * 删除货品
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String cargoIds) {
		Map<String, Object> result = new HashMap<>();
		try{
			boolean flag = cargoService.delete(Utils.stringArr2longArr(StringUtils.split(cargoIds, ",")));
			if(flag) {
				result.put("code", 1);
				result.put("msg", "删除成功");
				return result;
			}
			result.put("msg", "删除失败");
		}catch(Exception e){
			result.put("msg", e.getMessage());
			logger.error("删除失败", e);
		}
		result.put("code", 0);
		return result;
	}
	
	/**
	 * 获取SKU列表
	 */
	@RequestMapping("/getSkuList")
	@ResponseBody
	public List<CargoSkuSimpleVo> getSkuList(long cargoId) {
		try{
			return cargoService.getSkuList(cargoId);
		}catch(Exception e){
			logger.error("获取SKU列表失败", e);
		}
		return new ArrayList<>();
	}
	
	/**
	 * 获取货品信息
	 */
	@RequestMapping("/getInfo")
	@ResponseBody
	public CargoInfoVo getInfo(long cargoId) {
		try{
			return cargoService.getCargoInfo(cargoId);
		}catch(Exception e){
			logger.error("获取货品信息失败", e);
		}
		return null;
	}
	
	/**
	 * 检查SKU是否可以删除
	 */
	@RequestMapping("/isSkuCanDelete")
	@ResponseBody
	public Map<String, Object> isSkuCanDelete(long skuId) {
		Map<String, Object> map = new HashMap<>();
		try{
			if(cargoService.isSkuCanDelete(skuId)){
				map.put("code", 1);
				map.put("msg", "可以删除");
				return map;
			}
		}catch(Exception e){
			map.put("msg", e.getMessage());
		}
		map.put("code", 0);
		return map;
	}

	/**
	 * 获取用户Id
	 * 
	 * @param request
	 * 
	 * @add by 2016-04-09
	 */
	@SuppressWarnings("unchecked")
	private long getUserId(HttpServletRequest request) {
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		long userId = loginMap.get("staffId") != null ? Long.valueOf(loginMap.get("staffId").toString()) : 0;
		return userId;
	}
}
