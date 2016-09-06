package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventOnlineStudyType;

public interface EventOnlineStudyTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventOnlineStudyType record);

    int insertSelective(EventOnlineStudyType record);

    EventOnlineStudyType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventOnlineStudyType record);

    int updateByPrimaryKey(EventOnlineStudyType record);
}