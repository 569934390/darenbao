package com.club.web.spread.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.spread.domain.MarketSpreadClassifyDo;
import com.club.web.spread.vo.MarketSpreadClassifyVo;

public interface SpreadClassifyRepository {
	List<MarketSpreadClassifyVo> selectAllSpreadClassify(Map<String, Object> map);
	Long querySpreadClassifyCountPage();
	int addSpreadClassify(MarketSpreadClassifyDo marketSpreadClassifyDo);
	int updateSpreadClassify(MarketSpreadClassifyDo marketSpreadClassifyDo);
	void deleteById(Long id);
	int updateStatusById(Long id,int status);
	List<MarketSpreadClassifyVo> findAllSpreadClassify();
	List<MarketSpreadClassifyVo> findAllSpreadClassifyOnStatus();
}
