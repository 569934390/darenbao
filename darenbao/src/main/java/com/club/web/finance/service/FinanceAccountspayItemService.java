package com.club.web.finance.service;

import java.util.List;
import java.util.Map;

import com.club.web.finance.vo.FinanceAccountspayItemVo;
import com.club.web.finance.vo.FinanceAccountspayVo;

public interface FinanceAccountspayItemService {

	public Map<String,Object> saveOrUpdate(FinanceAccountspayItemVo financeAccountspayItemVo);
	
	public FinanceAccountspayItemVo getFinanceAccountspayItemVo(long mainid);
}
