package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.store.dao.base.TradeHeadStoreMapper;
import com.club.web.store.vo.TradeHeadStoreVo;

public interface TradeHeadStoreExtendMapper extends TradeHeadStoreMapper{

	List<Map<String,Object>> queryTradeHeadStorePage(Map<String, Object> map);

	Long queryTradeHeadStoreCountPage(Map<String, Object> map);

	List<TradeHeadStoreVo> queryTradeHeadStoreByName(String name);

	List<TradeHeadStoreVo> queryTradeHeadStoreByOwner(Long owner);
	
}