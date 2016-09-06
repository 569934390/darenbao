package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.GoodReceiptAddr;

public interface GoodReceiptAddrMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodReceiptAddr record);

    int insertSelective(GoodReceiptAddr record);

    GoodReceiptAddr selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodReceiptAddr record);

    int updateByPrimaryKey(GoodReceiptAddr record);
}