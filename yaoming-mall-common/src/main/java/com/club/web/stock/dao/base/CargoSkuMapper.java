package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSku;

public interface CargoSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSku record);

    int insertSelective(CargoSku record);

    CargoSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSku record);

    int updateByPrimaryKey(CargoSku record);
}