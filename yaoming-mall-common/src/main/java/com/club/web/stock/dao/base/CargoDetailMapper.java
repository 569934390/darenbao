package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoDetail;

public interface CargoDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoDetail record);

    int insertSelective(CargoDetail record);

    CargoDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoDetail record);

    int updateByPrimaryKeyWithBLOBs(CargoDetail record);

    int updateByPrimaryKey(CargoDetail record);
}