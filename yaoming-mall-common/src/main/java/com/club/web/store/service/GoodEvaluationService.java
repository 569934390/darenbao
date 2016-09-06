package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.web.store.vo.GoodEvaluationVo;

public interface GoodEvaluationService {
	void saveEvaluation(GoodEvaluationVo goodEvaluationVo, String[] urlArray, long userId);

	List<GoodEvaluationVo> selectEvaluationByGoodId(long goodId);

	/**
	 * 根据Id删除商品评价
	 * 
	 * @param id
	 * @return Map<String, Object>
	 * @add by 2016-04-25
	 */
	Map<String, Object> deleteEvaluationSer(String id);
}
