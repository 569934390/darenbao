package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.store.dao.base.CarriageRuleMapper;
import com.club.web.store.vo.CarriageRuleVo;

public interface CarriageRuleExtendMapper extends CarriageRuleMapper{
	List<CarriageRuleVo> selectCarriageRule(Map<String, Object> map);
	Long selectGoodsColumnCountPage(Map<String, Object> map);
	List<CarriageRuleVo> getCarriageRuleList();
	Long getGoodsColumnByCarriageRuleId(Long carriageRuleId);
}