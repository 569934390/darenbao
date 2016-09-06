package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoInboundDetail;

public interface CargoInboundDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoInboundDetail record);

    int insertSelective(CargoInboundDetail record);

    CargoInboundDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoInboundDetail record);

    int updateByPrimaryKey(CargoInboundDetail record);
}