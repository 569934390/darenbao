package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.GoodEvaluationMapper;
import com.club.web.store.vo.GoodEvaluationVo;

public interface GoodEvaluationExtendMapper extends GoodEvaluationMapper{
	public List<GoodEvaluationVo> selectEvaluationByGoodId(@Param("goodId")long goodId);
	public void deleteBySkuId(@Param("goodSkuId")long id);
	public void deleteByGoodId(@Param("goodId")long goodId);
}