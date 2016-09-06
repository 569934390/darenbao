package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.GoodEvaluation;

public interface GoodEvaluationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodEvaluation record);

    int insertSelective(GoodEvaluation record);

    GoodEvaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodEvaluation record);

    int updateByPrimaryKey(GoodEvaluation record);
}