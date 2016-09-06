package com.club.web.module.dao.base;

import com.club.web.module.dao.base.po.Opinion;

public interface OpinionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Opinion record);

    int insertSelective(Opinion record);

    Opinion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Opinion record);

    int updateByPrimaryKeyWithBLOBs(Opinion record);

    int updateByPrimaryKey(Opinion record);
}