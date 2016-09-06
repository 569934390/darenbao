package com.club.web.store.dao.extend;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.club.web.store.dao.base.CarriageRuleDetailMapper;
import com.club.web.store.vo.CarriageRuleDetailVo;

public interface CarriageRuleDetailExtendMapper extends CarriageRuleDetailMapper {
	
	List<CarriageRuleDetailVo> selectCarriageRuleDetailListByCarriageRuleId(Long carriageRuleId);
	
	int deleteCarriageRuleDetailByCarriageRuleId(Long carriageRuleId);
	
	BigDecimal getCarriageByRegionId(Map<String, Object> carriageRuleId);
}