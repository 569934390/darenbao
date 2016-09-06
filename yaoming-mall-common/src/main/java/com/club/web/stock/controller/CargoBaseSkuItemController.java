package com.club.web.stock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.JsonUtil;
import com.club.web.stock.service.CargoBaseSkuItemService;
import com.club.web.stock.vo.CargoBaseSkuItemVo;

/**   
* @Title: CargoBaseSkuItemController.java
* @Package com.club.web.stock.controller 
* @Description: 商品基础规格选项接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Controller
@RequestMapping("/cargoBaseSkuItemController")
public class CargoBaseSkuItemController {
	
	@Autowired
	private CargoBaseSkuItemService cargoBaseSkuItemService;
	
	/**
     * 新增规格选项
     * @param baseSkuType 规格对象值
     */
	@RequestMapping("/addCargoBaseSkuItem")
	@ResponseBody
	public CargoBaseSkuItemVo addBaseSkuItem(String modelJson) {
		CargoBaseSkuItemVo baseSkuItem = JsonUtil.toBean(modelJson, CargoBaseSkuItemVo.class);
		CargoBaseSkuItemVo newCargoBaseSkuItemVo = new CargoBaseSkuItemVo();
		if(baseSkuItem != null){
			newCargoBaseSkuItemVo = cargoBaseSkuItemService.addBaseSkuItem(baseSkuItem);			
		} else{
			newCargoBaseSkuItemVo = null;
		}
				
		return newCargoBaseSkuItemVo;
	}
	
	/**
     * 根据条件查询规格选项
     * @param skuType 规格类型ID
     * @return 规格选项列表
     */
	@RequestMapping("/selectSkuItemBySkuTypeId")
	@ResponseBody
	public List<CargoBaseSkuItemVo> selectSkuItemBySkuTypeId(Long id, String type) {
		List<CargoBaseSkuItemVo> resultLst = new ArrayList<CargoBaseSkuItemVo>();
		resultLst = cargoBaseSkuItemService.selectSkuItemBySkuTypeId(id,type);	
		
		return resultLst;
	}
    
    /**
     * 删除规格选项
     * @param id 规格类型ID
     * @return
     */
	@RequestMapping("/deleteSkuItemByBaseSkuTypeId")
	@ResponseBody
    public Map<String, Object> deleteSkuItemByBaseSkuTypeId(String modelJson) {
		Map<String, Object> result = new HashMap<String, Object>();	//返回信息
		CargoBaseSkuItemVo baseSkuItem = JsonUtil.toBean(modelJson, CargoBaseSkuItemVo.class);
		if(baseSkuItem != null){
			Long id = Long.valueOf(baseSkuItem.getId());
			Long baseSkuTypeId = Long.valueOf(baseSkuItem.getBaseSkuTypeId());
			if(id != null){
				result.put("success", true);
				result.put("msg", "删除成功");
				cargoBaseSkuItemService.deleteSkuItemByBaseSkuTypeId(baseSkuTypeId,id);			
			} else{
				result.put("success", false);
				result.put("msg", "规格类型ID不能为空");
			}			
		}
				
		return result;
	}

}
