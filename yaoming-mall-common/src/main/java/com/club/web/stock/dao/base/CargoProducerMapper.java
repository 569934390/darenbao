package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoProducer;

public interface CargoProducerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoProducer record);

    int insertSelective(CargoProducer record);

    CargoProducer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoProducer record);

    int updateByPrimaryKey(CargoProducer record);
}