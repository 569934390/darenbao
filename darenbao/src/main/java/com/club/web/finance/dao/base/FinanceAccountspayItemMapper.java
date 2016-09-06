package com.club.web.finance.dao.base;

import com.club.web.finance.dao.base.po.FinanceAccountspayItem;

public interface FinanceAccountspayItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FinanceAccountspayItem record);

    int insertSelective(FinanceAccountspayItem record);

    FinanceAccountspayItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinanceAccountspayItem record);

    int updateByPrimaryKey(FinanceAccountspayItem record);
}