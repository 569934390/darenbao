package com.club.web.stock.domain.repository;

import java.util.List;

import com.club.web.stock.domain.CargoBaseSkuItemDo;
import com.club.web.stock.vo.CargoBaseSkuItemVo;

/**   
* @Title: CargoBaseSkuItemRepository.java
* @Package com.club.web.stock.domain.repository
* @Description: 商品基础规格选项domain接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface CargoBaseSkuItemRepository {
	
	/**
	 * 新增规格选项
	 * @param baseSkuType 规格选项对象
	 * @return
	 */
	int addBaseSkuItem(CargoBaseSkuItemDo baseSkuItem);
		
	/**
	 * 删除规格选项
	 * @param id 规格类型ID
	 * @return
	 */
	int deleteSkuItemByBaseSkuTypeId(Long baseSkuTypeId, Long id);
	
	/**
	 * 根据条件查询规格选项
	 * @param id 规格类型ID
	 * @return 规格选项列表
	 */
	List<CargoBaseSkuItemVo> selectSkuItemBySkuTypeId(Long id);
	
	List<CargoBaseSkuItemVo> selectSkuItemAndImgBySkuTypeId(Long id);
	
	CargoBaseSkuItemDo create();
	
	CargoBaseSkuItemVo selectSkuItemById(Long id);
}
