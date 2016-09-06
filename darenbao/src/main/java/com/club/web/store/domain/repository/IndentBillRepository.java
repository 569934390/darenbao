package com.club.web.store.domain.repository;

import java.util.Map;

import com.club.web.store.domain.IndentBillDo;
import com.club.web.store.vo.IndentBillVo;

public interface IndentBillRepository {
	int insert(IndentBillDo indentBillDo);
	int update(IndentBillDo indentBillDo);
	IndentBillDo voChangeDo(IndentBillVo indentBillVo);
	IndentBillDo create();
	Map<String, Object> getShopIdAndSkuId(long shopId, long skuId);
}