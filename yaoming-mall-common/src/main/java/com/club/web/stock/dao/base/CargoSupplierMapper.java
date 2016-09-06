package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoSupplier;

public interface CargoSupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoSupplier record);

    int insertSelective(CargoSupplier record);

    CargoSupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoSupplier record);

    int updateByPrimaryKey(CargoSupplier record);
}