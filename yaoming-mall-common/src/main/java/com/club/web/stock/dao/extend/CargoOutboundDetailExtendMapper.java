package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoOutboundDetailMapper;
import com.club.web.stock.dao.base.po.CargoOutboundDetail;
import com.club.web.stock.domain.CargoOutboundDetailDo;
import com.club.web.stock.vo.StockGoodsOutboundMsgVo;

public interface CargoOutboundDetailExtendMapper extends CargoOutboundDetailMapper {

	CargoOutboundDetail selectBySkuAndOutId(@Param("skuId") long skuId, @Param("outboundId") long outboundId);

	int queryOutboundDetailTotal(@Param("nodeList") List<Long> nodeList, @Param("brand") long brand,
			@Param("outboundId") long outboundId, @Param("matchParam") String matchParam);

	List<StockGoodsOutboundMsgVo> queryOutboundDetail(@Param("nodeList") List<Long> nodeList,
			@Param("brand") long brand, @Param("outboundId") long outboundId, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	int queryOutboundSkuMsgTotal(@Param("goodsIdList") List<Long> goodsIdList);

	List<StockGoodsOutboundMsgVo> queryOutboundSkuMsg(@Param("goodsIdList") List<Long> goodsIdList,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	int deleteOutboundDetailByIds(@Param("ids") List<Long> ids);

	int delOutboundDetailByOutIds(@Param("outboundIds") List<Long> outboundIds);

	List<StockGoodsOutboundMsgVo> getStockCountByOutIds(@Param("outboundIds") List<Long> outboundIds);

	List<CargoOutboundDetailDo> queryOutDetailByOutboundId(@Param("outboundId") long outboundId);

	List<Long> queryOutDetailByOutId(@Param("outboundId") long outboundId);
}