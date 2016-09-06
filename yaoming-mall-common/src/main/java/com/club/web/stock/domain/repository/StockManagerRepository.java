package com.club.web.stock.domain.repository;

import java.util.List;

import com.club.web.stock.domain.CargoInboundDetailDo;
import com.club.web.stock.domain.CargoInboundOrderDo;
import com.club.web.stock.domain.CargoOutboundDetailDo;
import com.club.web.stock.domain.CargoOutboundOrderDo;
import com.club.web.stock.domain.CargoSkuStockDo;
import com.club.web.stock.domain.CargoSkuStockLogDo;
import com.club.web.stock.vo.CargoInboundDetailVo;
import com.club.web.stock.vo.CargoInboundOrderVo;
import com.club.web.stock.vo.CargoOutboundOrderVo;
import com.club.web.stock.vo.CargoSkuItemVo;
import com.club.web.stock.vo.CargoSkuStockVo;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.stock.vo.StockGoodsInboundMsgVo;
import com.club.web.stock.vo.StockGoodsOutboundMsgVo;

/**
 * 库存管理模块仓库
 * 
 * @author wqh
 * 
 * @add by 2016-03-22
 */
public interface StockManagerRepository {

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-03-31
	 */
	<T> void save(T t) throws Exception;

	/**
	 * 根据条件查询库存对象
	 * 
	 * @param id
	 * @return CargoSkuStockDo
	 * @add by 2016-03-29
	 */
	CargoSkuStockDo queryStockById(long id) throws Exception;

	/**
	 * 根据条件查询库存总条数
	 * 
	 * @param nodeList
	 * @param brand-品牌Id
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-29
	 */
	int queryStockMsgTotal(List<Long> nodeList, long brand, String matchParam);

	/**
	 * 根据条件查询库存信息
	 * 
	 * @param nodeList
	 * @param brand-品牌Id
	 * @param matchParam-模糊参数
	 * @return List<CargoSkuStockVo>
	 * @add by 2016-03-29
	 */
	List<CargoSkuStockVo> queryStockMsg(List<Long> nodeList, long brand, String matchParam, int startIndex,
			int pageSize);

	/**
	 * 创建库存对象
	 * 
	 * @param skuId
	 * @param count
	 * @param userId
	 * @return CargoSkuStockDo
	 * @add by 2016-03-26
	 */
	CargoSkuStockDo createStockObj(long skuId, int count, long userId);

	/**
	 * 创建库存日志对象
	 * 
	 * @param stock
	 * @param status
	 * @param inboundId
	 * @param count
	 * @return CargoSkuStockLogDo
	 * @add by 2016-04-14
	 */
	CargoSkuStockLogDo createStockLogObj(CargoSkuStockDo stock, int status, long inboundId, int count);

	/**
	 * 更新库存信息
	 * 
	 * @param obj
	 * @return void
	 * @add by 2016-03-29
	 */
	void updateStock(CargoSkuStockDo obj) throws Exception;

	/**
	 * 更新出库明细信息
	 * 
	 * @param obj
	 * @return void
	 * @add by 2016-03-29
	 */
	void updateOutbound(CargoOutboundDetailDo obj) throws Exception;

	/**
	 * 更新入库单状态
	 * 
	 * @param inboundObj
	 * @return void
	 * @add by 2016-03-26
	 */
	void updateInboundOrderStatus(CargoInboundOrderDo inboundObj) throws Exception;

	/**
	 * 删除入库单信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-03-27
	 */
	boolean deleteInboundOrder(List<Long> ids);

	/**
	 * 删除入库单详细信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-03-27
	 */
	boolean deleteInboundOrderDetailByIds(List<Long> ids);

	/**
	 * 删除入库单详细信息
	 * 
	 * @param inboundIds
	 * @return boolean
	 * @add by 2016-03-27
	 */
	boolean deleteInboundOrderDetail(List<Long> inboundIds);

	/**
	 * 根据Id查询入库单对象信息
	 * 
	 * @param ids
	 * @return List<CargoInboundOrderDo>
	 * @add by 2016-03-26
	 */
	List<CargoInboundOrderDo> getInboundObjByIds(List<Long> ids);

	/**
	 * 更新入库单明细信息
	 * 
	 * @param detailObj
	 * @return void
	 * @add by 2016-03-26
	 */
	void updateInboundDetail(CargoInboundDetailDo detailObj) throws Exception;

	/**
	 * 查询入库单-根据查询条件
	 * 
	 * @param status-状态
	 * @param matchParam-模糊查询
	 * @param startIndex-开始记录数
	 * @param pageSize-页面数据大小
	 * @return List<CargoInboundOrderVo>
	 * @add by 2016-03-23
	 */
	List<CargoInboundOrderVo> queryInboundOrderList(int status, String matchParam, int startIndex, int pageSize);

	/**
	 * 查询入库单总数-根据查询条件
	 * 
	 * @param status-状态
	 * @param matchParam-模糊查询
	 * @return int
	 * @add by 2016-03-23
	 */
	int queryInboundOrderTotal(int status, String matchParam);

	/**
	 * 查询入库单明细总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param brand-品牌Id
	 * @param inboundId-入库单
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-24
	 */
	int queryInboundDetailTotal(List<Long> nodeList, long brand, long inboundId, String matchParam);

	/**
	 * 查询出库单明细总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param brand-品牌Id
	 * @param outboundId-出库单
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-24
	 */
	int queryOutboundDetailTotal(List<Long> nodeList, long brand, long outboundId, String matchParam);

	/**
	 * 查询出库单明细信息-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param brand-品牌Id
	 * @param outboundId-出库单
	 * @param matchParam-模糊参数
	 * @param startIndex
	 * @param pageSize
	 * @return List<StockGoodsOutboundMsgVo>
	 * @add by 2016-03-24
	 */
	List<StockGoodsOutboundMsgVo> queryOutboundDetail(List<Long> nodeList, long brand, long outboundId,
			String matchParam, int startIndex, int pageSize);

	/**
	 * 添加入库-查询货品信息总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param matchParam-模糊参数
	 * @return int
	 * @add by 2016-03-24
	 */
	int queryGoodsMsgTotal(List<Long> nodeList, String matchParam);

	/**
	 * 添加入库-查询货品Sku信息总数
	 * 
	 * @param goodsIdList
	 * @return int
	 * @add by 2016-03-25
	 */
	int queryGoodsSkuMsgTotal(List<Long> goodsIdList);

	/**
	 * 添加出库-查询货品Sku信息总数
	 * 
	 * @param goodsIdList
	 * @return int
	 * @add by 2016-04-01
	 */
	int queryOutboundSkuMsgTotal(List<Long> goodsIdList);

	/**
	 * 添加出库-查询货品Sku信息
	 * 
	 * @param goodsIdList
	 * @return List<StockGoodsOutboundMsgVo>
	 * @add by 2016-04-01
	 */
	List<StockGoodsOutboundMsgVo> queryOutboundSkuMsg(List<Long> goodsIdList, int startIndex, int pageSize);

	/**
	 * 查询入库单明细总数-根据查询条件
	 * 
	 * @param nodeList-分类Id
	 * @param brand-品牌Id
	 * @param inboundId-入库单
	 * @param matchParam-模糊参数
	 * @param startIndex-开始记录数
	 * @param pageSize-每页几条
	 * @return List<StockGoodsInboundMsgVo>
	 * @add by 2016-03-24
	 */
	List<StockGoodsInboundMsgVo> queryInboundorderDetailList(List<Long> nodeList, long brand, long inboundId,
			String matchParam, int startIndex, int pageSize);

	/**
	 * 添加入库/出库-查询货品信息
	 * 
	 * @param nodeList-分类Id
	 * @param matchParam-模糊参数
	 * @param startIndex-开始记录数
	 * @param pageSize-每页几条
	 * @return List<StockGoodsInboundMsgVo>
	 * @add by 2016-03-24
	 */
	List<StockGoodsInboundMsgVo> queryGoodsMsg(List<Long> nodeList, String matchParam, int startIndex, int pageSize);

	/**
	 * 添加入库-查询货品Sku信息
	 * 
	 * @param goodsIdList-货品Id
	 * @param startIndex
	 * @param pageSize
	 * @return List<StockGoodsInboundMsgVo>
	 * @add by 2016-03-25
	 */
	List<StockGoodsInboundMsgVo> queryGoodsSkuMsg(List<Long> goodsIdList, int startIndex, int pageSize);

	/**
	 * 根据SKU查询规格
	 * 
	 * @param skuId
	 * @return List<CargoSkuItemVo>
	 * @add by 2016-03-24
	 */
	List<CargoSkuItemVo> queryGoodsSpecList(long skuId);

	/**
	 * 创建入库单对象
	 * 
	 * @param skuIdList-skuId
	 * @param skuCountList-sku数量
	 * @param userId
	 * @param sourceCode-来源编号
	 * @param remk-备注
	 * @return CargoInboundOrderDo
	 * @add by 2016-03-26
	 */
	CargoInboundOrderDo createInboundObj(long[] skuIdList, int[] skuCountList, long userId, String sourceCode,
			String remk);

	/**
	 * 创建出库单对象
	 * 
	 * @param skuIdList-skuId
	 * @param skuCountList-sku数量
	 * @param userId
	 * @param sourceCode-来源编号
	 * @param remk-备注
	 * @return CargoOutboundOrderDo
	 * @add by 2016-03-26
	 */
	CargoOutboundOrderDo createOutboundObj(long[] skuIdList, int[] skuCountList, long userId, String sourceCode,
			String remk);

	/**
	 * 创建出库单对象
	 * 
	 * @param list
	 * @param userId
	 * @param orderId-来源编号
	 * @param remk-备注
	 * @return CargoOutboundOrderDo
	 * @add by 2016-03-26
	 */
	CargoOutboundOrderDo createOutboundObj(List<SkuGoodsParam> list, long userId, String orderId, String remk);

	/**
	 * 创建入库单明细对象
	 * 
	 * @param skuIdList-skuId
	 * @param skuCountList-sku数量
	 * @param inboundId
	 * @return List<CargoInboundDetailDo>
	 * @add by 2016-03-26
	 */
	List<CargoInboundDetailDo> createInboundDetailObj(long[] skuIdList, int[] skuCountList, long inboundId);

	/**
	 * 创建出库单明细对象
	 * 
	 * @param skuIdList-skuId
	 * @param skuCountList-sku数量
	 * @param outboundId
	 * @return List<CargoOutboundDetailDo>
	 * @add by 2016-03-26
	 */
	List<CargoOutboundDetailDo> createOutboundDetailObj(long[] skuIdList, int[] skuCountList, long outboundId);

	/**
	 * 根据入库单id查询shuId,count列表
	 * 
	 * @param inboundId
	 * @return List<CargoInboundDetailVo>
	 * @add by 2016-03-29
	 */
	List<CargoInboundDetailVo> queryByInboundId(long inboundId);

	/**
	 * 查询存在异常的库存信息
	 * 
	 * @param ids
	 * @return List<CargoSkuStockVo>
	 * @add by 2016-03-30
	 */
	List<CargoSkuStockVo> queryNormalStockMsgByIds(List<Long> ids);

	/**
	 * 查询出库单信息总数
	 * 
	 * @param status
	 * @param matchParam
	 * @return int
	 * @add by 2016-03-31
	 */
	int queryOutboundOrderListTotal(int status, String matchParam);

	/**
	 * 查询出库单信息
	 * 
	 * @param status
	 * @param matchParam
	 * @param startIndex
	 * @param pageSize
	 * @return List<CargoOutboundOrderVo>
	 * @add by 2016-03-31
	 */
	List<CargoOutboundOrderVo> queryOutboundOrderList(int status, String matchParam, int startIndex, int pageSize);

	/**
	 * 删除出库单详细信息
	 * 
	 * @param ids
	 * @param boolean
	 * @add by 2016-04-01
	 */
	boolean deleteOutboundDetailByIds(List<Long> ids);

	/**
	 * 删除出库单详细信息根据外键
	 * 
	 * @param outboundIds
	 * @return boolean
	 * @add by 2016-04-01
	 */
	boolean delOutboundDetailByOutIds(List<Long> outboundIds);

	/**
	 * 删除出库单信息
	 * 
	 * @param ids
	 * @return boolean
	 * @add by 2016-04-01
	 */
	boolean deleteOutboundOrder(List<Long> ids);

	/**
	 * 根据Id查询出库单对象信息
	 * 
	 * @param ids
	 * @return List<CargoOutboundOrderDo>
	 * @add by 2016-04-01
	 */
	List<CargoOutboundOrderDo> getOutboundObjByIds(List<Long> ids);

	/**
	 * 更新出库单状态
	 * 
	 * @param outbound
	 * @return void
	 * @add by 2016-03-26
	 */
	void updateOutbound(CargoOutboundOrderDo outbound) throws Exception;

	/**
	 * 根据skuId查询库存数量
	 * 
	 * @param skuId
	 * @return int
	 * @add by 2016-03-26
	 */
	int queryStockCountBySkuId(long skuId);

	/**
	 * 根据skuId/outboundId查询出库数量
	 * 
	 * @param skuId
	 * @param outboundId
	 * @return int
	 * @add by 2016-04-04
	 */
	int queryOutCountBySkuId(long skuId, long outboundId);

	/**
	 * 根据出库单Id查询库存和出库单数量
	 * 
	 * @param outboundIds
	 * @return List<StockGoodsOutboundMsgVo>
	 * @add by 2016-04-01
	 */
	List<StockGoodsOutboundMsgVo> getStockCountByOutIds(List<Long> outboundIds);

	/**
	 * 通过SKUID查询库存对象
	 * 
	 * @param skuId
	 * @return CargoSkuStockDo
	 * @add by 2016-04-01
	 */
	CargoSkuStockDo queryBySkuId(long skuId) throws Exception;

	/**
	 * 根据outboundId查询出库单明细信息
	 * 
	 * @param outboundId
	 * @return List<CargoOutboundDetailDo>
	 * @add by 2016-04-04
	 */
	List<CargoOutboundDetailDo> queryOutDetailByOutboundId(long outboundId);

	/**
	 * 根据来源单号查询出库单
	 * 
	 * @param sourceNo
	 * @return List<CargoOutboundDetailDo>
	 * @add by 2016-04-08
	 */
	CargoOutboundOrderDo queryOutboundBySourceNo(String sourceNo);

	/**
	 * 根据skuId查询总库存量
	 * 
	 * @param skuId
	 * @return int
	 * @add by 2016-04-09
	 */
	int queryStockTotalBySkuId(long skuId);

	/**
	 * 根据skuId删除库存
	 * 
	 * @param skuId
	 * @return void
	 * @add by 2016-04-09
	 */
	void deleteStockBySkuId(long skuId);

	/**
	 * 根据cargoId删除库存
	 * 
	 * @param skuId
	 * @return void
	 * @add by 2016-04-09
	 */
	void deleteStockByCargoId(long cargoId);

	/**
	 * 根据cargoId查询总库存量
	 * 
	 * @param cargoId
	 * @return int
	 * @add by 2016-04-09
	 */
	int queryStockTotalByCargoId(long cargoId);

}
