package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.SalesReturnReason;

public interface SalesReturnReasonMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SalesReturnReason record);

    int insertSelective(SalesReturnReason record);

    SalesReturnReason selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SalesReturnReason record);

    int updateByPrimaryKey(SalesReturnReason record);
}