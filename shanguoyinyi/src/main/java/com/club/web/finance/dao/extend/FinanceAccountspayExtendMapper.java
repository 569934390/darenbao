package com.club.web.finance.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.finance.dao.base.FinanceAccountspayMapper;
import com.club.web.finance.vo.FinanceAbnormal;
import com.club.web.finance.vo.FinanceAccountspayVo;

public interface FinanceAccountspayExtendMapper extends FinanceAccountspayMapper{

	FinanceAccountspayVo getVoByOrderId(@Param("orderid")String orderid);

	FinanceAccountspayVo getVoById(@Param("Id")String Id);
	
	List<FinanceAccountspayVo> selectByU8OrderNull();
    
    List<FinanceAccountspayVo> selectByU8AccountsNull();

	int queryTotalByMap(Map<String, Object> con);

	List<FinanceAbnormal> queryListByMap(Map<String, Object> con);
   
}