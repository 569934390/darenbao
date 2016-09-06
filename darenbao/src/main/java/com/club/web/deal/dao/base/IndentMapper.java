package com.club.web.deal.dao.base;

import com.club.web.deal.dao.base.po.Indent;

public interface IndentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Indent record);

    int insertSelective(Indent record);

    Indent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Indent record);

    int updateByPrimaryKeyWithBLOBs(Indent record);

    int updateByPrimaryKey(Indent record);
}