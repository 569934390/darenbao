package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.StoreLevel;

public interface StoreLevelMapper {
    int deleteByPrimaryKey(Long levelId);

    int insert(StoreLevel record);

    int insertSelective(StoreLevel record);

    StoreLevel selectByPrimaryKey(Long levelId);

    int updateByPrimaryKeySelective(StoreLevel record);

    int updateByPrimaryKeyWithBLOBs(StoreLevel record);

    int updateByPrimaryKey(StoreLevel record);
}