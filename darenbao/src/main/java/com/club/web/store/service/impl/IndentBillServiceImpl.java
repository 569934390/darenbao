package com.club.web.store.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.store.domain.IndentBillDo;
import com.club.web.store.domain.repository.IndentBillRepository;
import com.club.web.store.service.IndentBillService;
import com.club.web.store.vo.IndentBillVo;

/**   
* @Title: IndentBillServiceImpl.java
* @Package com.club.web.store.service.impl
* @Description: 结算账单Service层实现类 
* @author hqLin 
* @date 2016/05/06
* @version V1.0   
*/
@Service("indentBillService")
public class IndentBillServiceImpl implements IndentBillService {

	@Autowired
	private IndentBillRepository indentBillRepository;

	@Override
	public int addIndentBill(IndentBillVo indentBillVo) {
		IndentBillDo indentBillDo = indentBillRepository.voChangeDo(indentBillVo);
		
		int index = indentBillDo.insert();
		return index;
	}
	
	@Override
	public int updateIndentBill(IndentBillVo indentBillVo) {
		IndentBillDo indentBillDo = indentBillRepository.voChangeDo(indentBillVo);
		
		int index = indentBillDo.update();
		return index;
	}

	@Override
	public Map<String, Object> getShopIdAndSkuId(long shopId, long skuId) {
		return indentBillRepository.getShopIdAndSkuId(shopId,skuId);
	}
}