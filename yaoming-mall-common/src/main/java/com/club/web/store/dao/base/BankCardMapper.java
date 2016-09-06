package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.BankCard;

public interface BankCardMapper {
    int deleteByPrimaryKey(Long bankCardId);

    int insert(BankCard record);

    int insertSelective(BankCard record);

    BankCard selectByPrimaryKey(Long bankCardId);

    int updateByPrimaryKeySelective(BankCard record);

    int updateByPrimaryKey(BankCard record);
}