package com.club.web.store.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.store.vo.SubbranchVo;

/**
 * @Title: SettlementBillService.java
 * @Package com.club.web.store.service
 * @Description: 待结算账单Service层接口类
 * @author hqLin
 * @date 2016/05/04
 * @version V1.0
 */
@Service
public interface SettlementBillService {

	Map<String, Object> selectSettlementBill(Page<Map<String, Object>> page, String orderId, String shopId,
			Date startTime, Date endTime, int status);

	List<SubbranchVo> getSubbranch();

	void settlementBill(String idStr, int status);

	void addSettlementBill(String indentId);

	String exportExcel(String orderId, String shopId, Date startTime, Date endTime, int status, 
			HttpServletRequest request) throws IOException;

	Map<String, Object> selectTotalBill();

	/**
	 * 查询店铺结算信息
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	Map<String, Object> getShopBill(long shopId);
	
	/**
	 * 查询店铺供货价
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	Map<String, Object> getShopIdAndSkuId(long shopId,long skuId);
	
	boolean indentExist(String indentId);
}