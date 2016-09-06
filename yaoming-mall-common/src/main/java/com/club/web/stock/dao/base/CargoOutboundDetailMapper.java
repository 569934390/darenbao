package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoOutboundDetail;

public interface CargoOutboundDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoOutboundDetail record);

    int insertSelective(CargoOutboundDetail record);

    CargoOutboundDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoOutboundDetail record);

    int updateByPrimaryKey(CargoOutboundDetail record);
}