package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSkuType;

public interface CargoSkuTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSkuType record);

    int insertSelective(CargoSkuType record);

    CargoSkuType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSkuType record);

    int updateByPrimaryKey(CargoSkuType record);
}