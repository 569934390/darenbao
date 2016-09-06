package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.BankCardMapper;
import com.club.web.store.vo.BankCardExtendVo;
import com.club.web.store.vo.BankCardVo;

public interface BankCardExtendMapper extends BankCardMapper {

	List<BankCardVo> selectByPage(@Param("name") String name, @Param("connectId") String connectId,
			@Param("start") int start, @Param("limit") int limit);

	int selectByPageCount(@Param("name") String name, @Param("connectId") String connectId);

	int selectByBanCard(@Param("bankCard") String bankCard);

	int deleteBySubbranchID(@Param("connectId") Long connectId);

	List<BankCardExtendVo> getBankCardList(@Param("shopId") long shopId, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	BankCardExtendVo getBankCardObj(@Param("id") long id);

}