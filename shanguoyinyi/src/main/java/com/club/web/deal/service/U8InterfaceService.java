package com.club.web.deal.service;

import java.util.Map;

import com.club.core.common.Page;

public interface U8InterfaceService {

	void account(String orderId);
	
	void addU8Order();
	
	void addU8Accept();

	Page<Map<String, Object>> u8FinanceAbnormallist(Page<Map<String, Object>> page);
}
