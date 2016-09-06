package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.TradeGoodSku;

public interface TradeGoodSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeGoodSku record);

    int insertSelective(TradeGoodSku record);

    TradeGoodSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeGoodSku record);

    int updateByPrimaryKeyWithBLOBs(TradeGoodSku record);

    int updateByPrimaryKey(TradeGoodSku record);
}