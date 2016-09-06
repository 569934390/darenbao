package com.club.web.spread.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.spread.domain.MarketSpreadManagerDo;
import com.club.web.spread.vo.SpreadVo;

public interface SpreadManagerRepository {
	List<SpreadVo> querySpreadPage(Map<String, Object> map);
    Long querySpreadCountPage(Map<String, Object> map);
    void addSpread(MarketSpreadManagerDo marketSpreadManagerDo);
	void deleteById(Long id);
	void update(MarketSpreadManagerDo marketSpreadManagerDo);
	List<SpreadVo> querySpreadList(Map<String, Object> map);
}
