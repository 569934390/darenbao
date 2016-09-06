package com.club.web.message.dao.base;

import com.club.web.message.dao.base.po.MessageContent;

public interface MessageContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageContent record);

    int insertSelective(MessageContent record);

    MessageContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageContent record);

    int updateByPrimaryKey(MessageContent record);
}