package com.club.web.spread.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.spread.vo.GoodandCargoSimpleInfoVo;
import com.club.web.spread.vo.MarketSpreadManagerVo;
import com.club.web.spread.vo.SpreadVo;

public interface SpreadManagerService {
	Page<Map<String, Object>> selectSpread(Page<Map<String, Object>> page, HttpServletRequest request);

	Page<GoodandCargoSimpleInfoVo> queryGoodandCargoList(Page<GoodandCargoSimpleInfoVo> page);

	void addSpread(SpreadVo spreadVo);

	void deleteSpread(String idStr);

	void updateSpread(SpreadVo spreadVo);

	List<SpreadVo> querySpreadList(Map<String, Object> map);

	/**
	 * 根据id查询推广信息
	 * 
	 * @param id
	 * @return MarketSpreadManagerVo
	 */
	MarketSpreadManagerVo getSpreadDetailSer(long id);

	MarketSpreadManagerVo getSpreadDetailSerandAddReadNum(long id);
}
