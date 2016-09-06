package com.club.web.spread.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.spread.dao.base.MarketSpreadClassifyMapper;
import com.club.web.spread.dao.base.po.MarketSpreadClassify;
import com.club.web.spread.vo.MarketSpreadClassifyVo;


public interface MarketSpreadClassifyExtendMapper extends MarketSpreadClassifyMapper{
   
	Long querySpreadClassifyCountPage();
	List<MarketSpreadClassifyVo> selectAllSpreadClassify(Map<String, Object> map);
	int updateStatusById(@Param("id") Long id, @Param("status") int status);
	List<MarketSpreadClassifyVo> findAllSpreadClassify();
	List<MarketSpreadClassifyVo> findAllSpreadClassifyOnStatus();
}