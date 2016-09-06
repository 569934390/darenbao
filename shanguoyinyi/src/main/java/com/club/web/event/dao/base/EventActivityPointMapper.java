package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventActivityPoint;

public interface EventActivityPointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventActivityPoint record);

    int insertSelective(EventActivityPoint record);

    EventActivityPoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventActivityPoint record);

    int updateByPrimaryKey(EventActivityPoint record);
}