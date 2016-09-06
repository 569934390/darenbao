package com.club.web.stock.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.stock.dao.base.CargoBaseSkuTypeMapper;
import com.club.web.stock.dao.base.po.CargoBaseSkuType;
import com.club.web.stock.vo.CargoBaseSkuTypeVo;

/**   
* @Title: CargoBaseSkuTypeExtendMapper.java
* @Package com.club.web.stock.dao.extend
* @Description: 商品基础规格dao扩展接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface CargoBaseSkuTypeExtendMapper extends CargoBaseSkuTypeMapper{
	
	List<CargoBaseSkuTypeVo> selectBySkuNameAndSkuType(Map<String, Object> map);
	
	CargoBaseSkuType selectCargoBaseSkuTypeById(Long id);
	
	Long queryCargoBaseSkuTypeCountPage(Map<String, Object> map);
	
	List<CargoBaseSkuTypeVo> selectCargoBaseSkuTypeList();
}