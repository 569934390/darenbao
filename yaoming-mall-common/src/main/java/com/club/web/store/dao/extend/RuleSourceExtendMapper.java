package com.club.web.store.dao.extend;

import java.util.List;

import com.club.web.store.dao.base.RuleSourceMapper;
import com.club.web.store.vo.RuleSourceVo;

/**   
* @Title: RuleSourceExtendMapper.java
* @Package com.club.web.store.dao.extend
* @Description: 规则来源dao扩展接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface RuleSourceExtendMapper extends RuleSourceMapper {
	
	List<RuleSourceVo> selectRuleSourceList(int ruleType);
}