package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSkuOption;

public interface CargoSkuOptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSkuOption record);

    int insertSelective(CargoSkuOption record);

    CargoSkuOption selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSkuOption record);

    int updateByPrimaryKey(CargoSkuOption record);
}