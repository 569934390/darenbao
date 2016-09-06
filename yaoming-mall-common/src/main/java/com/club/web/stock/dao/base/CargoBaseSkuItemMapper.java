package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoBaseSkuItem;

public interface CargoBaseSkuItemMapper {
	
    int deleteByPrimaryKey(Long id);
    
    int insert(CargoBaseSkuItem record);
    
    int insertSelective(CargoBaseSkuItem record);
    
    CargoBaseSkuItem selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(CargoBaseSkuItem record);
    
    int updateByPrimaryKey(CargoBaseSkuItem record);
}