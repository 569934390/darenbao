package com.club.web.alipay.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.club.web.alipay.dao.AlipayDao;

@Repository("alipayDao")
@Transactional
public class AlipayDaoImpl implements AlipayDao {
	
	@Override
	public String getGoodsData(String goodsId) {
		//select DB
		return "";
	}
	
	public void AddOrderData() {
		//insert DB
	}


	public int findByOrderNum(String orderNum) {
		//select DB
		return 0;
	}
	
	public void updateOrderState(String orderNum) {
		//update DB
	}
}
