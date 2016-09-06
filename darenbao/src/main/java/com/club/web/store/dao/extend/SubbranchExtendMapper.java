package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.BankCardMapper;
import com.club.web.store.dao.base.SubbranchMapper;
import com.club.web.store.dao.base.po.BankCard;
import com.club.web.store.dao.base.po.Subbranch;
import com.club.web.store.domain.SubbranchDo;
import com.club.web.store.vo.SubbranchVo;

public interface SubbranchExtendMapper extends SubbranchMapper {

	List<Subbranch> selectByPage(@Param("condition") String condition, @Param("loginName") String loginName,
			@Param("start") int start, @Param("limit") int limit);

	int selectByPageCount(@Param("condition") String condition, @Param("loginName") String loginName);

	List<Subbranch> getSubbranchName(@Param("condition") String condition, @Param("start") int start,
			@Param("limit") int limit);

	int checkNumber(Map<String, Object> map);

	String getShopProtocol(@Param("shopId") long shopId);

	Subbranch selectByNumber(@Param("number") String number);

	List<SubbranchVo> findByLoginNameAndPass(@Param("loginName") String loginName, @Param("password") String password);

	SubbranchDo getSubbranchById(@Param("shopId") long shopId);

	List<SubbranchVo> selectByMobile(@Param("mobile") String mobile);

	int checkLoginName(@Param("loginName") String loginName);

	List<SubbranchVo> getSubbranchListForActivity();
}