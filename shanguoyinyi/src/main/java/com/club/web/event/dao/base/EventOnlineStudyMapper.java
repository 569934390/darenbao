package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventOnlineStudy;

public interface EventOnlineStudyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventOnlineStudy record);

    int insertSelective(EventOnlineStudy record);

    EventOnlineStudy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventOnlineStudy record);

    int updateByPrimaryKeyWithBLOBs(EventOnlineStudy record);

    int updateByPrimaryKey(EventOnlineStudy record);
}