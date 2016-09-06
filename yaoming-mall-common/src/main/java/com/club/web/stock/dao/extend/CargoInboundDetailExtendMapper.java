package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoInboundDetailMapper;
import com.club.web.stock.dao.base.po.CargoInboundDetail;
import com.club.web.stock.vo.CargoInboundDetailVo;

public interface CargoInboundDetailExtendMapper extends CargoInboundDetailMapper {

	int deleteInboundDetailByIds(@Param("ids") List<Long> ids);

	int deleteInboundDetail(@Param("inboundIds") List<Long> inboundIds);

	CargoInboundDetail selectBySkuId(@Param("skuId") long skuId, @Param("inbound") long inbound);

	List<CargoInboundDetailVo> querySkuIdsByInboundId(@Param("inboundId") long inboundId);
}