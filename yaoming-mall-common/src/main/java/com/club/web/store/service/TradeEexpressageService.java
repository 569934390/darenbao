package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.store.vo.TradeEexpressageVo;

public interface TradeEexpressageService {

	Map<String,Object> saveOrUpdateExpressage( TradeEexpressageVo TradeEexpressageVo);
	
	Page getExpressageList(Page page);
	
	List<TradeEexpressageVo> getExpressageUseList(Page page);
	
	int deleteExpressage(String[] expressageIds);
	
	int updateExpressageState(String[] expressageId , String state);
	
	
	
}
