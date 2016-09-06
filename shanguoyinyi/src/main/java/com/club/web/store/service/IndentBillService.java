package com.club.web.store.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.club.web.store.vo.IndentBillVo;

/**   
* @Title: IndentBillService.java
* @Package com.club.web.store.service.impl
* @Description: 结算账单Service层接口类 
* @author hqLin 
* @date 2016/05/06
* @version V1.0   
*/
@Service
public interface IndentBillService {
	int addIndentBill(IndentBillVo indentBillVo);
	int updateIndentBill(IndentBillVo indentBillVo);
	Map<String, Object> getShopIdAndSkuId(long shopId, long skuId);
}