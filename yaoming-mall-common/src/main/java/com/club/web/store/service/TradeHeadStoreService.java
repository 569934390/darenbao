package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.store.vo.TradeHeadStoreVo;

public interface TradeHeadStoreService {

	TradeHeadStoreVo getTradeHeadStoreVoById(Long id);

	Map<String, Object> saveOrUpdateTradeHeadStore(TradeHeadStoreVo tradeHeadStoreVo, HttpServletRequest request);

	Map<String, Object> deleteTradeHeadStore(String idStr);

	Page<Map<String, Object>> queryTradeHeadStorePage(Page<Map<String, Object>> page, HttpServletRequest request);

	Map<String,Object> updateTradeHeadStoreStatue(String idStr, Long statue,HttpServletRequest request);
	
	public Long getStaffHeadStoreId(HttpServletRequest request);
}
