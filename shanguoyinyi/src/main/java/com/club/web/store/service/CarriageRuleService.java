package com.club.web.store.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.store.vo.CarriageRuleDetailVo;
import com.club.web.store.vo.CarriageRuleVo;

/**   
* @Title: CarriageRuleService.java
* @Package com.club.web.store.service 
* @Description: 包邮规则 Service接口类 
* @author hqLin   
* @date 2016/07/19
* @version V1.0   
*/
public interface CarriageRuleService {
	
	Page<Map<String, Object>> selectCarriageRule(Page<Map<String, Object>> page);
	
	Map<String, Object> addOrUpdCarriageRule(String carriageRuleId, String templateName, BigDecimal carriage, 
			List<CarriageRuleDetailVo> CarriageRuleDetailList);
	
	Map<String, Object> queryCarriageRuleDetail(String carriageRuleId);
	
	Map<String, Object> deleteCarriageRule(String carriageRuleId);
	
	List<CarriageRuleVo> getCarriageRuleList();
	
	BigDecimal getCarriageByRegionId(List<Map> list);
	
	BigDecimal getCarriageByRegionIdAndGoodId(List<Map> list);
}