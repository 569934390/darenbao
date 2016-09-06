package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.TradeGood;

public interface TradeGoodMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeGood record);

    int insertSelective(TradeGood record);

    TradeGood selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeGood record);

    int updateByPrimaryKey(TradeGood record);
}