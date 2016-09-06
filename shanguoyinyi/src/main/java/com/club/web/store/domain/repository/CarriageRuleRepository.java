package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.CarriageRuleDo;
import com.club.web.store.vo.CarriageRuleVo;

public interface CarriageRuleRepository {

	int insertCarriageRule(CarriageRuleDo carriageRuleDo);

	int updateCarriageRule(CarriageRuleDo carriageRuleDo);

	int deleteCarriageRule(Long id);
	
	List<CarriageRuleVo> selectCarriageRule(Map<String, Object> map);
	
	Long selectCarriageRuleCountPage(Map<String, Object> map);
	
	CarriageRuleDo createCarriageRuleDo();
	
	CarriageRuleDo voChangeDo(CarriageRuleVo carriageRuleVo);
	
	CarriageRuleVo selectCarriageRuleById(Long id);
	
	List<CarriageRuleVo> getCarriageRuleList();
	
	Long getGoodsColumnByCarriageRuleId(Long carriageRuleId);
}