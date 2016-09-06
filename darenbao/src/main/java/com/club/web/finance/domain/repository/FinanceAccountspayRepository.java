package com.club.web.finance.domain.repository;

import java.util.List;

import com.club.web.finance.domain.FinanceAccountspayDo;
import com.club.web.finance.vo.FinanceAccountspayVo;

public interface FinanceAccountspayRepository {

	FinanceAccountspayDo create(FinanceAccountspayVo financeAccountspayVo);

	void save(FinanceAccountspayDo financeAccountspayDo);

	FinanceAccountspayDo load(long parseLong);

	void update(FinanceAccountspayDo financeAccountspayDo);

	FinanceAccountspayVo getVoByOrderId(String orderId);
	
	List<FinanceAccountspayVo> selectByU8OrderNull();
	
	List<FinanceAccountspayVo> selectByU8AccountsNull();

}
