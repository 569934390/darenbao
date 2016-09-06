package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.DeliveryAddressMapper;
import com.club.web.store.dao.base.po.DeliveryAddress;
import com.club.web.store.dao.extend.DeliveryAddressExtendMapper;
import com.club.web.store.domain.DeliveryAddressDo;
import com.club.web.store.domain.repository.DeliveryAddressRepository;
@Repository
public class DeliveryAddressRepositoryImpl implements DeliveryAddressRepository{

	@Autowired
	DeliveryAddressExtendMapper deliveryAddressExtendMapper;
	DeliveryAddress deliveryAddress=new DeliveryAddress();
	
	@Override
	public int saveDeliveryAddress(DeliveryAddressDo deliveryAddressDo) {
		BeanUtils.copyProperties(deliveryAddressDo, deliveryAddress);
		int result=deliveryAddressExtendMapper.insert(deliveryAddress);
		return result;
	}

	@Override
	public int updateDeliveryAddress(DeliveryAddressDo deliveryAddressDo) {
		BeanUtils.copyProperties(deliveryAddressDo, deliveryAddress);
		int result=deliveryAddressExtendMapper.updateByPrimaryKeySelective(deliveryAddress);
		return result;
	}

}
