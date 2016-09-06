package com.club.web.store.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.club.web.store.domain.CarriageRuleDetailDo;
import com.club.web.store.vo.CarriageRuleDetailVo;

public interface CarriageRuleDetailRepository {

	int insertCarriageRuleDetail(CarriageRuleDetailDo carriageRuleDetailDo);

	int updateCarriageRuleDetail(CarriageRuleDetailDo carriageRuleDetailDo);

	int deleteCarriageRuleDetail(Long id);
	
	CarriageRuleDetailDo voChangeDo(CarriageRuleDetailVo carriageRuleDetailVo);
	
	List<CarriageRuleDetailVo> selectCarriageRuleDetailListByCarriageRuleId(Long carriageRuleId);
	
	int deleteCarriageRuleDetailByCarriageRuleId(Long carriageRuleId);
	
	BigDecimal getCarriageByRegionId(Map<String, Object> carriageRuleId);
}