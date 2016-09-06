package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.IndentBill;

public interface IndentBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IndentBill record);

    int insertSelective(IndentBill record);

    IndentBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IndentBill record);

    int updateByPrimaryKey(IndentBill record);
}