package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventActivityComment;

public interface EventActivityCommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventActivityComment record);

    int insertSelective(EventActivityComment record);

    EventActivityComment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventActivityComment record);

    int updateByPrimaryKey(EventActivityComment record);
}