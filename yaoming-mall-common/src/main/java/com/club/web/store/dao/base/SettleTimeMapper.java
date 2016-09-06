package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.SettleTime;

public interface SettleTimeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SettleTime record);

    int insertSelective(SettleTime record);

    SettleTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SettleTime record);

    int updateByPrimaryKey(SettleTime record);
}