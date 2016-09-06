package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoOutboundOrderMapper;
import com.club.web.stock.domain.CargoOutboundOrderDo;
import com.club.web.stock.vo.CargoOutboundOrderVo;

public interface CargoOutboundOrderExtendMapper extends CargoOutboundOrderMapper {
	int queryOutboundOrderListTotal(@Param("status") int status, @Param("matchParam") String matchParam);

	List<CargoOutboundOrderVo> queryOutboundOrderList(@Param("status") int status,
			@Param("matchParam") String matchParam, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	int deleteOutboundOrder(@Param("ids") List<Long> ids);

	List<CargoOutboundOrderDo> getOutboundObjByIds(@Param("ids") List<Long> ids);

	CargoOutboundOrderDo queryOutboundBySourceNo(@Param("sourceNo") String sourceNo);
}