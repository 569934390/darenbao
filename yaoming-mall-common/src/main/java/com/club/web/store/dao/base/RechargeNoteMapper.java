package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.RechargeNote;

public interface RechargeNoteMapper {
    int deleteByPrimaryKey(Long indentId);

    int insert(RechargeNote record);

    int insertSelective(RechargeNote record);

    RechargeNote selectByPrimaryKey(Long indentId);

    int updateByPrimaryKeySelective(RechargeNote record);

    int updateByPrimaryKey(RechargeNote record);
}