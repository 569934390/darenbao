/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.domain.repository;

import java.util.List;

import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.domain.TradeGoodSkuDo;
import com.club.web.store.vo.TradeGoodSkuVo;

/**
 *@Title:
 *@Description:domain层的仓库接口
 *@Author:CZJ
 *@Since:2016年3月26日
 *@Version:1.1.0
 */
public interface GoodSKURepository {
	void addGoodSKU(TradeGoodSkuDo tradeGoodSkuDo);
	void updateTradeGoodSKU(TradeGoodSkuDo tradeGoodSkuDo);
    void deleteTradeGoodSKU(long id);
    void deleteSkuByGoodId(Long goodId);
    void updateSkuByGoodId(TradeGoodSkuDo tradeGoodSkuDo);
    public List<TradeGoodSkuVo> selectGoodSkuByGoodId(long goodId);
    void updateNumById(List<GoodUpDo> list);
    void updateSaleNumById(List<GoodUpDo> list);
    TradeGoodSkuVo selectSkuById(Long id);
    List<TradeGoodSkuDo> ifGoodSkuExgist(long cargoSkuId);
    List<TradeGoodSkuVo> selectSkuList(long goodId);
}
