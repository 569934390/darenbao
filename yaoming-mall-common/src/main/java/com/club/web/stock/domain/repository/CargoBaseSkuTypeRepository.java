package com.club.web.stock.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.stock.domain.CargoBaseSkuTypeDo;
import com.club.web.stock.vo.CargoBaseSkuTypeVo;

/**   
* @Title: CargoBaseSkuTypeRepository.java
* @Package com.club.web.stock.domain.repository
* @Description: 商品基础规格domain接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface CargoBaseSkuTypeRepository {
	
	/**
	 * 新增规格类型
	 * @param baseSkuType 规格对象
	 * @return
	 */
	int addBaseSkuType(CargoBaseSkuTypeDo baseSkuType);
	
	/**
	 * 编辑规格类型
	 * @param baseSkuType 规格对象
	 * @return
	 */
	int editBaseSkuType(CargoBaseSkuTypeDo baseSkuType);
	
	/**
	 * 删除规格类型
	 * @param id 规格ID
	 * @return
	 */
	int deleteBaseSkuType(Long id);
	
	/**
	 * 根据条件查询规格
	 * @param skuName 规格名称
	 * @param skuType 规格类型
	 * @return 规格列表
	 */
	List<CargoBaseSkuTypeVo> selectBySkuNameAndSkuType(Map<String, Object> map);
	
	CargoBaseSkuTypeDo voChangeDo(CargoBaseSkuTypeVo cargoBaseSkuTypeVo);
	
	CargoBaseSkuTypeDo create();
	
	/**
	 * 根据ID查询规格类型
	 * @param id
	 * @return
	 */
	CargoBaseSkuTypeDo selectCargoBaseSkuTypeById(Long id);
	
	Long queryCargoBaseSkuTypeCountPage(Map<String, Object> map);
	
	/**
	 * 查询所有的规格
	 * @return
	 */
	List<CargoBaseSkuTypeVo> selectCargoBaseSkuTypeList();
}
