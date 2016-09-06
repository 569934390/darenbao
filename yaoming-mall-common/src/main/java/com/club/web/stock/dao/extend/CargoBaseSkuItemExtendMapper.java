package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoBaseSkuItemMapper;
import com.club.web.stock.vo.CargoBaseSkuItemVo;

/**   
* @Title: CargoBaseSkuItemExtendMapper.java
* @Package com.club.web.stock.dao.extend
* @Description: 商品基础规格选项dao扩展接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface CargoBaseSkuItemExtendMapper extends CargoBaseSkuItemMapper{
	
	int deleteByBaseSkuTypeId(@Param("baseSkuTypeId") Long baseSkuTypeId, @Param("id") Long id);
	
	List<CargoBaseSkuItemVo> selectSkuItemBySkuTypeId(@Param("baseSkuTypeId") Long baseSkuTypeId);
	
	List<CargoBaseSkuItemVo> selectSkuItemAndImgBySkuTypeId(@Param("baseSkuTypeId") Long baseSkuTypeId);
}