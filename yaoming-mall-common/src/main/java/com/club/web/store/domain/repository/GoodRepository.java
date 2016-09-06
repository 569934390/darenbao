/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.domain.repository;
import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.domain.TradeGoodDo;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.TradeGoodVo;

/**
 *@Title:
 *@Description:
 *@Author:Administrator
 *@Since:2016年3月25日
 *@Version:1.1.0
 */
public interface GoodRepository {
	
	void addGood(TradeGoodDo tradeGoodDo);
	void updateTradeGood(TradeGoodDo tradeGoodDo);
	public void deleteTradeGood(long id);
	List<GoodVo> queryGoodPage(Page<Map<String, Object>> page);
    Long queryGoodCountPage(Page<Map<String, Object>> page);
    void updateStatus(Map<String,Object>map);
    Long ifGoodExgist(long cargoId);
    List<GoodListVo> queryGoodList(Map<String,Object> map);
    public TradeGoodVo queryTradeGoodInfo(long goodId);
	List<GoodVo> selectGoodIdByCategory(Long category);
    void updateSaleNumById(List<GoodUpDo> list);
}
