package com.club.web.module.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.module.dao.base.OpinionMapper;
import com.club.web.module.dao.base.po.Opinion;
import com.club.web.module.vo.ClientVo;
/**
 * 意见反馈Dao扩展类
 * @author zhuzd
 *
 */
public interface OpinionExtendMapper extends OpinionMapper{

	ClientVo findClientVoById(Long id);

	int queryTotalByMap(Map<String, Object> map);

	List<Opinion> queryOpinionPoByMap(Map<String, Object> map);

	int deleteByIds(List<Long> ids);

	ClientVo findWeixinClientVoById(Long id);


}