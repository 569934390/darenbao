package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSkuStock;

public interface CargoSkuStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSkuStock record);

    int insertSelective(CargoSkuStock record);

    CargoSkuStock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSkuStock record);

    int updateByPrimaryKey(CargoSkuStock record);
}