package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoOutboundOrder;

public interface CargoOutboundOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoOutboundOrder record);

    int insertSelective(CargoOutboundOrder record);

    CargoOutboundOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoOutboundOrder record);

    int updateByPrimaryKey(CargoOutboundOrder record);
}