package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.CargoBrand;

public interface CargoBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CargoBrand record);

    int insertSelective(CargoBrand record);

    CargoBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CargoBrand record);

    int updateByPrimaryKey(CargoBrand record);
}