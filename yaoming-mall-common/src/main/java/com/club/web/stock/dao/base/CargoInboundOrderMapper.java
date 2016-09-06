package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoInboundOrder;

public interface CargoInboundOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoInboundOrder record);

    int insertSelective(CargoInboundOrder record);

    CargoInboundOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoInboundOrder record);

    int updateByPrimaryKey(CargoInboundOrder record);
}