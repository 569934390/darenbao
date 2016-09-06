package com.club.web.store.domain.repository;

import java.util.List;

import com.club.web.store.domain.RuleSourceDo;
import com.club.web.store.vo.RuleSourceVo;

/**   
* @Title: RuleSourceRepository.java
* @Package com.club.web.store.domain.repository
* @Description: 规则来源domain接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/
public interface RuleSourceRepository {
	
	int addRuleSource(RuleSourceDo ruleSourceDo);
	
	int deleteRuleSource(Long id);
	
	List<RuleSourceVo> selectRuleSourceList(int ruleType);
}
