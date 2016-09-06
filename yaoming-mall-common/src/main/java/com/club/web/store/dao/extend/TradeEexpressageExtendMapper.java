package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.po.BankCard;
import com.club.web.store.dao.base.po.TradeEexpressage;

import com.club.web.store.dao.base.TradeEexpressageMapper;

public interface TradeEexpressageExtendMapper extends TradeEexpressageMapper {
	List<TradeEexpressage> selectByPage(@Param("state") int state,@Param("start") int start, @Param("limit") int limit);

	int selectByPageCount(@Param("state") int state,@Param("name") String name);
	
	
}