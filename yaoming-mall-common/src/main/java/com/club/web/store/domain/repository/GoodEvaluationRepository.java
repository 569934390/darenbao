package com.club.web.store.domain.repository;

import java.util.List;

import com.club.web.store.domain.GoodEvaluationDo;
import com.club.web.store.vo.GoodEvaluationVo;

/**
 * 
 * @author czj 商品评价的仓库接口
 */
public interface GoodEvaluationRepository {
	void saveEvaluation(GoodEvaluationDo goodEvaluationDo);

	List<GoodEvaluationVo> selectEvaluationByGoodId(long goodId);

	public void deleteBySkuId(long goodSkuId);

	public void deleteByGoodId(long goodId);

	void deleteById(long id);
}
