package com.club.web.stock.service;

import java.util.List;
import java.util.Map;

import com.club.web.stock.vo.CargoBaseSkuItemVo;

/**   
* @Title: CargoBaseSkuItemService.java
* @Package com.club.web.stock.service 
* @Description: 商品基础规格选项Service接口类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface CargoBaseSkuItemService {
	
	/**
	 * 新增规格选项
	 * @param baseSkuItem 规格选项对象
	 * @return
	 */
	CargoBaseSkuItemVo addBaseSkuItem(CargoBaseSkuItemVo baseSkuItem);
		
	/**
	 * 删除规格选项
	 * @param id 规格类型ID
	 * @return
	 */
	Map<String, Object> deleteSkuItemByBaseSkuTypeId(Long baseSkuTypeId, Long id);
	
	/**
	 * 根据条件查询规格选项
	 * @param id 规格类型ID
	 * @return 规格选项列表
	 */
	List<CargoBaseSkuItemVo> selectSkuItemBySkuTypeId(Long id, String type);
}
