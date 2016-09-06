package com.club.web.store.service;

import java.util.Map;

import com.club.web.store.vo.DeliveryAddressVo;

public interface DeliveryAddressService {
    Map<String,Object> saveOrUpdateDeliveryAddress(DeliveryAddressVo deliveryAddressVo);
    
    DeliveryAddressVo getDeliveryAddress();
}
