package com.club.web.spread.dao.base;

import com.club.web.spread.dao.base.po.MarketSpreadManager;

public interface MarketSpreadManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketSpreadManager record);

    int insertSelective(MarketSpreadManager record);

    MarketSpreadManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketSpreadManager record);

    int updateByPrimaryKeyWithBLOBs(MarketSpreadManager record);

    int updateByPrimaryKey(MarketSpreadManager record);
}