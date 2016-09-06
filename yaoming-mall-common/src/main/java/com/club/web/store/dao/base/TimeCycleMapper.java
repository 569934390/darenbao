package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.TimeCycle;

public interface TimeCycleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TimeCycle record);

    int insertSelective(TimeCycle record);

    TimeCycle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TimeCycle record);

    int updateByPrimaryKey(TimeCycle record);
}