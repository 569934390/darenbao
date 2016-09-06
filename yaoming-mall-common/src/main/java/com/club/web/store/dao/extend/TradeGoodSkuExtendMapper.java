package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.TradeGoodSkuMapper;
import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.domain.TradeGoodSkuDo;
import com.club.web.store.vo.TradeGoodSkuVo;

public interface TradeGoodSkuExtendMapper extends TradeGoodSkuMapper{
    void deleteSkuByGoodId(@Param("goodId")Long goodId);
    void updateSkuByGoodId(TradeGoodSkuDo tradeGoodSkuDo);
    List<TradeGoodSkuVo> selectSkuByGoodId(@Param("goodId")Long goodId);
    void updateNumById(GoodUpDo  goodUpDo);
    void updateSaleNumById(GoodUpDo  goodUpDo);
    TradeGoodSkuVo selectById(@Param("id")Long id);
    List<TradeGoodSkuDo> selectGoodSkuNumsByCargoSkuId(@Param("cargoSkuId")Long cargoSkuId);
    List<TradeGoodSkuVo> selectSkuList(@Param("goodId")Long goodId);
}