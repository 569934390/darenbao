package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.RuleValue;
import com.club.web.store.dao.extend.RuleValueExtendMapper;
import com.club.web.store.domain.RuleValueDo;
import com.club.web.store.domain.repository.RuleValueRepository;

/**   
* @Title: RuleSourceRepositoryImpl.java
* @Package com.club.web.store.dao.repository
* @Description: 规则值domain接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Repository
public class RuleValueRepositoryImpl implements RuleValueRepository {

	@Autowired
	RuleValueExtendMapper ruleValueDao;
	
	@Override
	public int addRuleValue(RuleValueDo ruleValueDo) {
		RuleValue ruleValue = new RuleValue();
		BeanUtils.copyProperties(ruleValueDo, ruleValue);
		
		return ruleValueDao.insert(ruleValue);
	}
	
	@Override
	public int deleteRuleValue(Long id) {
		
		return ruleValueDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateRuleValue(RuleValueDo ruleValueDo) {
		RuleValue ruleValue = new RuleValue();
		BeanUtils.copyProperties(ruleValueDo, ruleValue);
		
		return ruleValueDao.updateByPrimaryKey(ruleValue);
	}
}
