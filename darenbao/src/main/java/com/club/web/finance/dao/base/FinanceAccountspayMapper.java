package com.club.web.finance.dao.base;

import com.club.web.finance.dao.base.po.FinanceAccountspay;

public interface FinanceAccountspayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FinanceAccountspay record);

    int insertSelective(FinanceAccountspay record);

    FinanceAccountspay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinanceAccountspay record);

    int updateByPrimaryKey(FinanceAccountspay record);
}