package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoBaseSkuType;

public interface CargoBaseSkuTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoBaseSkuType record);

    int insertSelective(CargoBaseSkuType record);

    CargoBaseSkuType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoBaseSkuType record);

    int updateByPrimaryKey(CargoBaseSkuType record);
}