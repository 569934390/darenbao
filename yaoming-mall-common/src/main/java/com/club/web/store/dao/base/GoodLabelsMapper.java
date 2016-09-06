package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.GoodLabels;

public interface GoodLabelsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodLabels record);

    int insertSelective(GoodLabels record);

    GoodLabels selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodLabels record);

    int updateByPrimaryKey(GoodLabels record);
}