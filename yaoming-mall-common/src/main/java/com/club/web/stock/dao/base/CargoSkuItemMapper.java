package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSkuItem;

public interface CargoSkuItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSkuItem record);

    int insertSelective(CargoSkuItem record);

    CargoSkuItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSkuItem record);

    int updateByPrimaryKey(CargoSkuItem record);
}