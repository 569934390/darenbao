package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventActivityUserinfo;

public interface EventActivityUserinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventActivityUserinfo record);

    int insertSelective(EventActivityUserinfo record);

    EventActivityUserinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventActivityUserinfo record);

    int updateByPrimaryKey(EventActivityUserinfo record);
}