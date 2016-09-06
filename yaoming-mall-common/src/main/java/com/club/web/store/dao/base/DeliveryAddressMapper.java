package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.DeliveryAddress;

public interface DeliveryAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeliveryAddress record);

    int insertSelective(DeliveryAddress record);

    DeliveryAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryAddress record);

    int updateByPrimaryKey(DeliveryAddress record);
}