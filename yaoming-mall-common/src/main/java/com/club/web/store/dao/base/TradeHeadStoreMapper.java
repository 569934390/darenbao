package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.TradeHeadStore;

public interface TradeHeadStoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeHeadStore record);

    int insertSelective(TradeHeadStore record);

    TradeHeadStore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeHeadStore record);

    int updateByPrimaryKey(TradeHeadStore record);
}