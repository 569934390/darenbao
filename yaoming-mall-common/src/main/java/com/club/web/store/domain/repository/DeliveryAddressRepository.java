package com.club.web.store.domain.repository;

import com.club.web.store.domain.BankCardDo;
import com.club.web.store.domain.DeliveryAddressDo;

public interface DeliveryAddressRepository {

	int saveDeliveryAddress(DeliveryAddressDo deliveryAddress);
	int updateDeliveryAddress(DeliveryAddressDo deliveryAddress);
	
}
