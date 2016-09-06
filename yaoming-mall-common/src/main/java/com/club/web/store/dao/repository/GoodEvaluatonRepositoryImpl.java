package com.club.web.store.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.GoodEvaluation;
import com.club.web.store.dao.extend.GoodEvaluationExtendMapper;
import com.club.web.store.domain.GoodEvaluationDo;
import com.club.web.store.domain.repository.GoodEvaluationRepository;
import com.club.web.store.vo.GoodEvaluationVo;

@Repository
public class GoodEvaluatonRepositoryImpl implements GoodEvaluationRepository {
	@Autowired
	private GoodEvaluationExtendMapper evaluationMapper;

	@Override
	public void saveEvaluation(GoodEvaluationDo goodEvaluationDo) {
		// TODO Auto-generated method stub
		GoodEvaluation goodEvaluation = new GoodEvaluation();
		BeanUtils.copyProperties(goodEvaluationDo, goodEvaluation);
		evaluationMapper.insert(goodEvaluation);
	}

	public List<GoodEvaluationVo> selectEvaluationByGoodId(long goodId) {
		return evaluationMapper.selectEvaluationByGoodId(goodId);
	}

	public void deleteBySkuId(long goodSkuId) {
		evaluationMapper.deleteBySkuId(goodSkuId);
	}

	public void deleteByGoodId(long goodId) {
		evaluationMapper.deleteByGoodId(goodId);
	}

	public void deleteById(long id) {
		evaluationMapper.deleteByPrimaryKey(id);
	}

}
