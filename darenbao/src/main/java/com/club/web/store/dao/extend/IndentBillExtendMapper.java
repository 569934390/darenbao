package com.club.web.store.dao.extend;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.IndentBillMapper;
import com.club.web.store.dao.base.po.IndentBill;

public interface IndentBillExtendMapper extends IndentBillMapper{
	int updateStatusByIndentId(IndentBill record);
	Map<String, Object> getShopIdAndSkuId(@Param("shopId")long shopId,@Param("skuId") long skuId);
}