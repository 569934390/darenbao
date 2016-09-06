package com.club.web.store.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.RuleSource;
import com.club.web.store.dao.extend.RuleSourceExtendMapper;
import com.club.web.store.domain.RuleSourceDo;
import com.club.web.store.domain.repository.RuleSourceRepository;
import com.club.web.store.vo.RuleSourceVo;

/**   
* @Title: RuleSourceRepositoryImpl.java
* @Package com.club.web.store.dao.repository
* @Description: 规则来源domain接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Repository
public class RuleSourceRepositoryImpl implements RuleSourceRepository {

	@Autowired
	RuleSourceExtendMapper ruleSourceDao;
	
	@Override
	public int addRuleSource(RuleSourceDo ruleSourceDo) {
		RuleSource ruleValue = new RuleSource();
		BeanUtils.copyProperties(ruleSourceDo, ruleValue);
		
		return ruleSourceDao.insert(ruleValue);
	}
	
	@Override
	public int deleteRuleSource(Long id) {
		
		return ruleSourceDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<RuleSourceVo> selectRuleSourceList(int ruleType) {
		
		return ruleSourceDao.selectRuleSourceList(ruleType);
	}
}