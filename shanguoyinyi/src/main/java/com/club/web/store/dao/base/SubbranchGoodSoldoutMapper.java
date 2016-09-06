package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.SubbranchGoodSoldout;

public interface SubbranchGoodSoldoutMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SubbranchGoodSoldout record);

    int insertSelective(SubbranchGoodSoldout record);

    SubbranchGoodSoldout selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SubbranchGoodSoldout record);

    int updateByPrimaryKey(SubbranchGoodSoldout record);
}