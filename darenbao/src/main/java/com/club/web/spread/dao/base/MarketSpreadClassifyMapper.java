package com.club.web.spread.dao.base;

import com.club.web.spread.dao.base.po.MarketSpreadClassify;

public interface MarketSpreadClassifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MarketSpreadClassify record);

    int insertSelective(MarketSpreadClassify record);

    MarketSpreadClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MarketSpreadClassify record);

    int updateByPrimaryKey(MarketSpreadClassify record);
}