package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.SalesReturnReasonMapper;
import com.club.web.store.dao.base.po.SalesReturnReason;
import com.club.web.store.dao.base.po.TradeEexpressage;
import com.club.web.store.vo.SalesReturnReasonVo;

public interface SalesReturnReasonExtendMapper extends SalesReturnReasonMapper{
	List<SalesReturnReason> selectByPage(@Param("start") int start, @Param("limit") int limit);

	int selectByPageCount(@Param("reason") String reason);

	List<SalesReturnReasonVo> findAll();
}