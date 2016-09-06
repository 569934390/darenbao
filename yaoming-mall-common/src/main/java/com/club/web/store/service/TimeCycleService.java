package com.club.web.store.service;

import java.util.Map;

import com.club.web.store.vo.TimeCycleVo;

public interface TimeCycleService {
	
	Map<String, Object> saveOrUpdate(TimeCycleVo timeCycle);

	TimeCycleVo detail(int dbDataByName);

	TimeCycleVo getSettleTime();

	TimeCycleVo getIndentShipTime();
}
