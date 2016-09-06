package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.GoodsBaseLabel;

public interface GoodsBaseLabelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsBaseLabel record);

    int insertSelective(GoodsBaseLabel record);

    GoodsBaseLabel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsBaseLabel record);

    int updateByPrimaryKey(GoodsBaseLabel record);
}