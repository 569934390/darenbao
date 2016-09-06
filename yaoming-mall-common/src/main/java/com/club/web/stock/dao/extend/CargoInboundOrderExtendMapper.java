/**
 * 
 */
package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoInboundOrderMapper;
import com.club.web.stock.domain.CargoInboundOrderDo;
import com.club.web.stock.vo.CargoInboundOrderVo;
import com.club.web.stock.vo.CargoSkuItemVo;
import com.club.web.stock.vo.StockGoodsInboundMsgVo;

public interface CargoInboundOrderExtendMapper extends CargoInboundOrderMapper {
	List<CargoInboundOrderDo> getInboundObjByIds(@Param("ids") List<Long> ids);

	List<StockGoodsInboundMsgVo> queryGoodsSkuMsg(@Param("goodsIdList") List<Long> goodsIdList,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	int queryGoodsSkuMsgTotal(@Param("goodsIdList") List<Long> goodsIdList);

	List<CargoInboundOrderVo> queryInboundOrderList(@Param("status") int status, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	int queryInboundOrderTotal(@Param("status") int status, @Param("matchParam") String matchParam);

	int queryGoodsMsgTotal(@Param("nodeList") List<Long> nodeList, @Param("matchParam") String matchParam);

	List<StockGoodsInboundMsgVo> queryGoodsMsg(@Param("nodeList") List<Long> nodeList, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	List<StockGoodsInboundMsgVo> queryInboundorderDetailList(@Param("nodeList") List<Long> nodeList,
			@Param("brand") long brand, @Param("inboundId") long inboundId, @Param("matchParam") String matchParam,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	List<CargoSkuItemVo> queryGoodsSpecList(@Param("skuId") long skuId);

	int queryInboundOrderDetailTotal(@Param("nodeList") List<Long> nodeList, @Param("brand") long brand,
			@Param("inboundId") long inboundId, @Param("matchParam") String matchParam);

	int deleteInboundStock(@Param("ids") List<Long> ids);
}
