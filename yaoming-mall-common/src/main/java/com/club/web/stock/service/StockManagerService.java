package com.club.web.stock.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.Page;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.stock.vo.TreeListVo;

/**
 * 库存管理-service
 * 
 * @author wqh
 * 
 * @add by 2016-03-20
 *
 */
public interface StockManagerService {

	/**
	 * 保存入库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-26
	 */
	Map<String, Object> saveInboundStockSer(Map<String, Object> paramMap, long userId) throws Exception;

	/**
	 * 修改入库单状态
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-26
	 */
	Map<String, Object> updateInboundStatusSer(Map<String, Object> paramMap, long userId) throws Exception;

	/**
	 * 删除入库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-27
	 */
	Map<String, Object> delInboundOrderSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 删除入库单明细信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-27
	 */
	Map<String, Object> delInboundOrderDetailSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询入库单
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-20
	 */
	Page<Map<String, Object>> queryInboundOrderListSer(Page<Map<String, Object>> page);

	/**
	 * 查询库存信息
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-29
	 */
	Page<Map<String, Object>> queryStockMsgSer(Page<Map<String, Object>> page);

	/**
	 * 查询入库单明细
	 * 
	 * @param page
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-20
	 */
	Page<Map<String, Object>> queryInboundOrderDetailSer(Page<Map<String, Object>> page);

	/**
	 * 添加入库-查询货品信息
	 * 
	 * @param page
	 *            -参数信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-25
	 */
	Page<Map<String, Object>> queryGoodsMsgSer(Page<Map<String, Object>> page);

	/**
	 * 添加入库-查询货品Sku信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-25
	 */
	Page<Map<String, Object>> queryGoodsSkuMsgSer(Page<Map<String, Object>> page);

	/**
	 * 添加出库-查询货品Sku信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-25
	 */
	Page<Map<String, Object>> queryOutboundStockSkuMsgSer(Page<Map<String, Object>> page);

	/**
	 * 修改库存留存数量
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-03-29
	 */
	Map<String, Object> updateStockRemainSer(Map<String, Object> paramMap, long userId) throws Exception;

	/**
	 * 更新存在异常的库存信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @add by 2016-03-30
	 */
	Map<String, Object> updateNormalStockMsgSer(Map<String, Object> paramMap, long userId) throws Exception;

	/**
	 * 查询出库单信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-31
	 */
	Page<Map<String, Object>> queryOutboundOrderListSer(Page<Map<String, Object>> page);

	/**
	 * 查询出库单详细信息
	 * 
	 * @param page
	 *            -分页信息
	 * @return Page<Map<String, Object>>
	 * @add by 2016-03-31
	 */
	Page<Map<String, Object>> queryOutboundOrderDetailSer(Page<Map<String, Object>> page);

	/**
	 * 保存出库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-03-31
	 */
	Map<String, Object> saveOutboundMsgSer(Map<String, Object> paramMap, long userId) throws Exception;

	/**
	 * 删除出库单明细信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-01
	 */
	Map<String, Object> delOutboundOrderDetailSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 删除出库单信息
	 * 
	 * @param paramMap
	 *            -参数
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-01
	 */
	Map<String, Object> delOutboundOrderSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 修改出库单状态-手动出库
	 * 
	 * @param paramMap
	 *            -参数
	 * @param userId
	 * @return Map<String, Object>
	 * @throws Exception
	 * @add by 2016-04-01
	 */
	Map<String, Object> updateOutboundStatusSerHandler(Map<String, Object> paramMap, long userId) throws Exception;

	/**
	 * 生成出库单/修改出库单状态/锁定库存-订单出库
	 * 
	 * @param list
	 *            -skuId列表
	 * @param userId
	 *            -(默认1-系统操作)
	 * @param status
	 *            -(4-待付款,5-已付款,6-出库,7-待付款取消,8-退款操作)
	 * @param orderId
	 *            -订单id
	 * @return Map<String, Object>
	 * @add by 2016-04-08
	 */
	Map<String, Object> updateStockByOrder(List<SkuGoodsParam> list, long userId, int status, long orderId)
			throws Exception;

	/**
	 * 查询树菜单
	 * 
	 * @return List<TreeListVo>
	 * @add by 2016-04-05
	 */
	List<TreeListVo> queryTreeMenuSer();

	/**
	 * 商品上下架
	 * 
	 * @param list
	 *            -参数列表
	 * @param userId
	 *            -用户Id
	 * @param status
	 *            (0-上架,1-下架)
	 * @return Map<String, Object>(code,msg)
	 * @add by 2016-04-05
	 */
	Map<String, Object> updateUpAndDownGoodsMsg(List<SkuGoodsParam> list, long userId, int status) throws Exception;

}
