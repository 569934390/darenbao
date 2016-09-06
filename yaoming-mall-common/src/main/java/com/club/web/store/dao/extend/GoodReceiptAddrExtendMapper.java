package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.GoodReceiptAddrMapper;
import com.club.web.store.domain.GoodReceiptAddrDo;
import com.club.web.store.vo.GoodReceiptAddrVo;

public interface GoodReceiptAddrExtendMapper extends GoodReceiptAddrMapper {
	List<GoodReceiptAddrDo> queryReceiptAddrByStatus(@Param("userId") long userId, @Param("status") int status);

	List<GoodReceiptAddrVo> queryReceiptAddrList(@Param("userId") long userId);

	GoodReceiptAddrVo getAddrById(@Param("id") long id);

	void deleteReceiptAddr(@Param("userId") long userId, @Param("ids") List<Long> ids);

	GoodReceiptAddrDo queryByCondition(@Param("userId") long userId, @Param("id") long id);

	List<GoodReceiptAddrDo> queryAllObj(@Param("userId") long userId);

	void updateNoExists(@Param("id") long id);
}