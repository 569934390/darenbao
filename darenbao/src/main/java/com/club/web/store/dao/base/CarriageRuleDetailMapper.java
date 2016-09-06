package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.CarriageRuleDetail;

public interface CarriageRuleDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarriageRuleDetail record);

    int insertSelective(CarriageRuleDetail record);

    CarriageRuleDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarriageRuleDetail record);

    int updateByPrimaryKey(CarriageRuleDetail record);
}