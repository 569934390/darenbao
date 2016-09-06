package com.club.web.store.domain.repository;

import com.club.web.store.domain.RuleValueDo;

/**   
* @Title: RuleValueRepository.java
* @Package com.club.web.store.domain.repository
* @Description: 规则值domain接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/
public interface RuleValueRepository {
	
	int addRuleValue(RuleValueDo ruleValueDo);
	
	int deleteRuleValue(Long id);
	
	int updateRuleValue(RuleValueDo ruleValueDo);
}
