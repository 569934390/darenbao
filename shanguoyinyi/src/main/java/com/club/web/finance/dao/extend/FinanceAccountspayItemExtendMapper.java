package com.club.web.finance.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.finance.dao.base.FinanceAccountspayItemMapper;
import com.club.web.finance.vo.FinanceAccountspayItemVo;

public interface FinanceAccountspayItemExtendMapper extends FinanceAccountspayItemMapper{
	
	FinanceAccountspayItemVo selectByMainId(@Param("mainid")long mainid);
}