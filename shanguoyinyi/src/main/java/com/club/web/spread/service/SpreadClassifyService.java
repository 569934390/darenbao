package com.club.web.spread.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.store.vo.GoodsBaseLabelVo;


public interface SpreadClassifyService {
	Page<Map<String, Object>> selectSpreadClassify(Page<Map<String, Object>> page, 
			HttpServletRequest request);
    Map<String, Object> addSpreadClassify(MarketSpreadClassifyVo marketSpreadClassifyVo, HttpServletRequest request);
	
	Map<String, Object> editSpreadClassify(MarketSpreadClassifyVo marketSpreadClassifyVo);
	
    Map<String, Object> deleteSpreadClassify(String idStr);
	
	Map<String, Object> changeStatus(String idStr, int status);
	
	List<MarketSpreadClassifyVo> findAllClassify();
	List<MarketSpreadClassifyVo> findAllClassifyOnStatus();
}