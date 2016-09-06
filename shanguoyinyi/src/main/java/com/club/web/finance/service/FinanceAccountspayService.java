package com.club.web.finance.service;

import java.util.List;
import java.util.Map;

import com.club.web.finance.domain.FinanceAccountspayDo;
import com.club.web.finance.vo.FinanceAccountspayVo;

public interface FinanceAccountspayService {
	
	public Map<String,Object> saveOrUpdate(FinanceAccountspayVo financeAccountspayVo);

	public FinanceAccountspayVo getVoByOrderId(String orderId);
	
	public FinanceAccountspayDo getVoById(String Id);
	
	public List<FinanceAccountspayVo> getVoByU8Missing(String u8Type);

	public Map<String,Object> updateByU8Account(String fanceId, String yongYouAccountId);

	public Map<String,Object> updateByU8Order(String fanceId, String yongYouOrderId);
	
	public Map<String,Object> updateByOrderId(String orderId, Integer statue);
}
