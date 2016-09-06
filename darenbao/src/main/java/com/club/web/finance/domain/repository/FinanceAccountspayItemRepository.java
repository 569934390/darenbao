package com.club.web.finance.domain.repository;

import java.util.List;

import com.club.web.finance.domain.FinanceAccountspayItemDo;
import com.club.web.finance.vo.FinanceAccountspayItemVo;

public interface FinanceAccountspayItemRepository {

	FinanceAccountspayItemDo create(FinanceAccountspayItemVo financeAccountspayItemVo);

	void save(FinanceAccountspayItemDo financeAccountspayItemDo);
	
	FinanceAccountspayItemVo getFinanceAccountspayItemVoByMain(long mainid);

}
