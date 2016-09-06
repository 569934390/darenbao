package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.RuleSource;

public interface RuleSourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RuleSource record);

    int insertSelective(RuleSource record);

    RuleSource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RuleSource record);

    int updateByPrimaryKey(RuleSource record);
}