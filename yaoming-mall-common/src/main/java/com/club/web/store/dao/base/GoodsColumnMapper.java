package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.GoodsColumn;

public interface GoodsColumnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsColumn record);

    int insertSelective(GoodsColumn record);

    GoodsColumn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsColumn record);

    int updateByPrimaryKey(GoodsColumn record);
}