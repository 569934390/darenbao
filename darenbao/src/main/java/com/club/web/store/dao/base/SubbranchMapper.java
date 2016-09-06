package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.Subbranch;

public interface SubbranchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Subbranch record);

    int insertSelective(Subbranch record);

    Subbranch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Subbranch record);

    int updateByPrimaryKey(Subbranch record);
}