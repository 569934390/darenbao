package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.TradeEexpressage;

public interface TradeEexpressageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeEexpressage record);

    int insertSelective(TradeEexpressage record);

    TradeEexpressage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeEexpressage record);

    int updateByPrimaryKey(TradeEexpressage record);
}