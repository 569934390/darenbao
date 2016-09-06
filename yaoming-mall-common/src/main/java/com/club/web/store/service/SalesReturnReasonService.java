package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.store.vo.SalesReturnReasonVo;

public interface SalesReturnReasonService {
	Map<String,Object> saveOrUpdateSalesReturnReason(SalesReturnReasonVo SalesReturnReasonVo);
	Page getSalesReturnReason(Page page);
	Map<String,Object> deletSalesReturnReason(String ids);
	List<SalesReturnReasonVo> findAll();
}
