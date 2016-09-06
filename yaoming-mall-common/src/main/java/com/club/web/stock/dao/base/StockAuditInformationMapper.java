package com.club.web.stock.dao.base;

import com.club.web.stock.dao.base.po.StockAuditInformation;

public interface StockAuditInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockAuditInformation record);

    int insertSelective(StockAuditInformation record);

    StockAuditInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockAuditInformation record);

    int updateByPrimaryKey(StockAuditInformation record);
}