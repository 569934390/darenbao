package com.club.web.store.dao.extend;

import java.util.List;

import com.club.web.store.dao.base.DeliveryAddressMapper;
import com.club.web.store.dao.base.po.DeliveryAddress;

public interface DeliveryAddressExtendMapper extends DeliveryAddressMapper{
	 List<DeliveryAddress> select();
}
