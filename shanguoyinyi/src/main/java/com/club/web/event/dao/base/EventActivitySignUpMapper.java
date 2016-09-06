package com.club.web.event.dao.base;

import com.club.web.event.dao.base.po.EventActivitySignUp;

public interface EventActivitySignUpMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EventActivitySignUp record);

    int insertSelective(EventActivitySignUp record);

    EventActivitySignUp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EventActivitySignUp record);

    int updateByPrimaryKey(EventActivitySignUp record);
}