package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSkuStockLog;

public interface CargoSkuStockLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSkuStockLog record);

    int insertSelective(CargoSkuStockLog record);

    CargoSkuStockLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSkuStockLog record);

    int updateByPrimaryKey(CargoSkuStockLog record);
}