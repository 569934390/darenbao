package com.club.web.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.web.stock.service.CargoBaseSkuTypeService;
import com.club.web.stock.vo.CargoBaseSkuTypeVo;
import com.club.web.util.IdGenerator;

/** 
* @Title: CargoBaseSkuTypeController.java
* @Package com.club.web.stock.controller
* @Description: 商品基础规格接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Controller
@RequestMapping("/cargoBaseSkuTypeController")
public class CargoBaseSkuTypeController {
	
	@Autowired
	private CargoBaseSkuTypeService cargoBaseSkuTypeService;
	
	/**
     * 新增/修改规格
     * @param modelJson 规格对象值字符串
     */
	@RequestMapping("/addOrUpdCargoBaseSkuType")
	@ResponseBody
	public Map<String, Object> addBaseSkuType(String modelJson) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		CargoBaseSkuTypeVo cargoBaseSkuTypeVo = JsonUtil.toBean(modelJson, CargoBaseSkuTypeVo.class);
		if(cargoBaseSkuTypeVo != null){
			if(cargoBaseSkuTypeVo.getId() != null && !cargoBaseSkuTypeVo.getId().isEmpty()){
				result.put("success", true);
				result.put("msg", "编辑成功");
				cargoBaseSkuTypeService.editBaseSkuType(cargoBaseSkuTypeVo);
			} else{
				result.put("success", true);
				result.put("msg", "新增成功");
				cargoBaseSkuTypeVo.setId(IdGenerator.getDefault().nextId() + "");
				cargoBaseSkuTypeService.addBaseSkuType(cargoBaseSkuTypeVo);				
			}
		} else{
			result.put("success", false);
			result.put("msg", "新增失败");
		}
				
		return result;
	}
	
	/**
     * 根据条件查询规格
     * @param skuName 规格名称
     * @param skuType 规格类型
     * @return 规格列表
     */
	@RequestMapping(value="/selectCargoBaseSkuType",method = RequestMethod.POST)
	@ResponseBody
	public Page<Map<String, Object>> selectBySkuNameAndSkuType(Page<Map<String, Object>> page, String skuName, String skuType,
			HttpServletResponse response) {
		page = cargoBaseSkuTypeService.selectBySkuNameAndSkuType(page, skuName, skuType);	
				
		return page;
	}
	    
    /**
     * 删除规格类型
     * @param idStr 规格ID
     * @return
     */
	@RequestMapping("/deleteCargoBaseSkuType")
	@ResponseBody
    public Map<String, Object> deleteBaseSkuType(String idStr) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		if(idStr != null){
			result.put("success", true);
			result.putAll(cargoBaseSkuTypeService.deleteBaseSkuType(idStr));
		} else{
			result.put("success", false);
			result.put("msg", "规格ID不能为空");
		}
			
		return result;
	}

	/**
	 * 获取所有规格类型
	 * @return
	 */
	@RequestMapping(value="/selectCargoBaseSkuTypeList")
	@ResponseBody
	public List<CargoBaseSkuTypeVo> selectCargoBaseSkuTypeList() {
				
		return cargoBaseSkuTypeService.selectCargoBaseSkuTypeList();
	}
}