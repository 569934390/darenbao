package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.store.domain.TradeHeadStoreDo;
import com.club.web.store.vo.TradeHeadStoreVo;

public interface TradeHeadStoreRepository {

	void insert(TradeHeadStoreDo tradeHeadStoreDo);
	
	void update(TradeHeadStoreDo tradeHeadStoreDo);

	TradeHeadStoreDo create(TradeHeadStoreVo TradeHeadStoreVo);

	TradeHeadStoreVo getTradeHeadStoreVoById(Long id);

	TradeHeadStoreDo getTradeHeadStoreDoById(long id);

	void deleteByPrimaryKey(long id);

	List<Map<String,Object>> queryTradeHeadStorePage(Page<Map<String, Object>> page, HttpServletRequest request);

	Long queryTradeHeadStoreCountPage(Page<Map<String, Object>> page, HttpServletRequest request);

	List<TradeHeadStoreVo> queryTradeHeadStoreByName(String name);

	List<TradeHeadStoreVo> queryTradeHeadStoreByOwner(Long owner);

}
