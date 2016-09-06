package com.club.web.deal.dao.base;

import com.club.web.deal.dao.base.po.IndentList;

public interface IndentListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IndentList record);

    int insertSelective(IndentList record);

    IndentList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IndentList record);

    int updateByPrimaryKey(IndentList record);
}