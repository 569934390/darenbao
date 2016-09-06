package com.club.web.module.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.module.domain.OpinionDo;
import com.club.web.module.vo.ClientVo;

/**
 * 意见反馈仓库接口
 * @author zhuzd
 *
 */
public interface OpinionRepository {

	public void add(OpinionDo opinionDo);
	
	public void modify(OpinionDo opinionDo);

	public OpinionDo findDoById(Long id);

	public ClientVo findClientVoById(Long clientId);

	public int queryTotalByMap(Map<String, Object> con);

	public List<OpinionDo> queryOpinionDoByMap(Map<String, Object> con);
	 
}
