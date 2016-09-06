package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoPic;

public interface CargoPicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoPic record);

    int insertSelective(CargoPic record);

    CargoPic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoPic record);

    int updateByPrimaryKey(CargoPic record);
}