package com.club.web.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.DeliveryAddress;
import com.club.web.store.dao.extend.DeliveryAddressExtendMapper;
import com.club.web.store.domain.DeliveryAddressDo;
import com.club.web.store.service.DeliveryAddressService;
import com.club.web.store.vo.DeliveryAddressVo;
import com.club.web.util.CommonUtil;

@Service("deliveryAddressService")
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

	@Autowired
	DeliveryAddressExtendMapper deliveryAddressExtendMapper;

	@Override
	public Map<String, Object> saveOrUpdateDeliveryAddress(DeliveryAddressVo deliveryAddressVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		DeliveryAddressDo deliveryAddressDo = new DeliveryAddressDo();
		BeanUtils.copyProperties(deliveryAddressVo, deliveryAddressDo);
		
    	
		List<DeliveryAddress> leliveryAddressList = deliveryAddressExtendMapper.select();
		if (leliveryAddressList.isEmpty()) {
			deliveryAddressDo.insert();
		} else {
			deliveryAddressDo.setId(leliveryAddressList.get(0).getId());
			deliveryAddressDo.update();
		}
		result.put("msg", "成功");
		result.put("success", true);
		return result;
	}

	@Override
	public DeliveryAddressVo getDeliveryAddress() {
		DeliveryAddressVo deliveryAddressVo;
		List<DeliveryAddress> leliveryAddressList = deliveryAddressExtendMapper.select();
		if (!leliveryAddressList.isEmpty()) {
			DeliveryAddress deliveryAddress=leliveryAddressList.get(0);
			deliveryAddressVo = new DeliveryAddressVo();
		    BeanUtils.copyProperties(deliveryAddress, deliveryAddressVo);
		if (deliveryAddress!=null) {
			deliveryAddressVo.setId(deliveryAddress.getId()+"");
		}
		}else{return null;}
		
		return deliveryAddressVo;
	}

}
