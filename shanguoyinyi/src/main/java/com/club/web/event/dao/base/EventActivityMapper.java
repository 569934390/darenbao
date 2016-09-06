package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventActivity;

public interface EventActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventActivity record);

    int insertSelective(EventActivity record);

    EventActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventActivity record);

    int updateByPrimaryKeyWithBLOBs(EventActivity record);

    int updateByPrimaryKey(EventActivity record);
}