package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.RuleValue;

public interface RuleValueMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RuleValue record);

    int insertSelective(RuleValue record);

    RuleValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleValue record);

    int updateByPrimaryKey(RuleValue record);
}