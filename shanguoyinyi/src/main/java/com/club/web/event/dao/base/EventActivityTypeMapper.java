package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventActivityType;

public interface EventActivityTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventActivityType record);

    int insertSelective(EventActivityType record);

    EventActivityType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventActivityType record);

    int updateByPrimaryKey(EventActivityType record);
}