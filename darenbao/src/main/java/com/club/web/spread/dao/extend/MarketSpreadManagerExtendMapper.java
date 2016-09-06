package com.club.web.spread.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.spread.dao.base.MarketSpreadManagerMapper;
import com.club.web.spread.vo.GoodandCargoSimpleInfoVo;
import com.club.web.spread.vo.MarketSpreadManagerVo;
import com.club.web.spread.vo.SpreadVo;

public interface MarketSpreadManagerExtendMapper extends MarketSpreadManagerMapper {
	Long querySpreadCountPage(Map<String, Object> map);

	List<SpreadVo> querySpreadPage(Map<String, Object> map);

	List<GoodandCargoSimpleInfoVo> queryGoodandCargoList(Map<String, Object> params);

	int queryGoodandCargoListCount(Map<String, Object> params);

	List<SpreadVo> querySpreadList(Map<String, Object> map);

	MarketSpreadManagerVo getSpreadDetail(@Param("id") long id);
}