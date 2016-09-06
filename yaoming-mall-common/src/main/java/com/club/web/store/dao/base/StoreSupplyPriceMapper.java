package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.StoreSupplyPrice;

public interface StoreSupplyPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StoreSupplyPrice record);

    int insertSelective(StoreSupplyPrice record);

    StoreSupplyPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StoreSupplyPrice record);

    int updateByPrimaryKey(StoreSupplyPrice record);
}