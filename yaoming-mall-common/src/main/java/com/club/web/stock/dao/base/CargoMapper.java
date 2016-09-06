package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.Cargo;

public interface CargoMapper {
	
    int deleteByPrimaryKey(Long id);
    int insert(Cargo record);
    int insertSelective(Cargo record);
    Cargo selectByPrimaryKey(Long id);
    int updateByPrimaryKeySelective(Cargo record);
    int updateByPrimaryKey(Cargo record);
}