package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.CarriageRule;

public interface CarriageRuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarriageRule record);

    int insertSelective(CarriageRule record);

    CarriageRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarriageRule record);

    int updateByPrimaryKey(CarriageRule record);
}