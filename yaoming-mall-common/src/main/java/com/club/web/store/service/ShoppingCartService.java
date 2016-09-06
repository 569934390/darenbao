package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.web.store.vo.ShoppingCartVo;

/**
 * 购物车-service接口
 * 
 * @author wqh
 * @add by 2016-04-07
 */
public interface ShoppingCartService {

	/**
	 * 根据用户Id查询购物车商品-最特色
	 * 
	 * @param userId
	 *            -用户Id
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-07
	 */
	List<ShoppingCartVo> queryShoppingCartByUserId(long userId, int optionType);

	/**
	 * 根据用户Id查询购物车商品-三国
	 * 
	 * 
	 * @param userId
	 *            -用户Id
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-07
	 */
	List<ShoppingCartVo> queryShoppingCartByUserId(long userId, long shopId, int optionType);

	/**
	 * 根据用户Id查询购物车商品-三国
	 * 
	 * 
	 * @param userId
	 * @param shopId
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-07
	 */
	int getCartCountSer(long userId, long shopId, int optionType);

	/**
	 * 根据用户Id添加购物车商品
	 * 
	 * @param userId
	 *            -用户Id
	 * @param goodsId
	 *            -商品Id
	 * @param shopId
	 *            -商店Id
	 * @param count
	 *            -商品数量
	 * @param prize
	 *            -商品价格
	 * @param optionType
	 * @param status
	 * @return Map<String, Object>
	 * @add by 2016-04-07
	 */
	Map<String, Object> addShoppingCartByUserId(long userId, String goodsId, String shopId, int count, double prize,
			int optionType, int status) throws Exception;

	/**
	 * 根据用户Id/商品Id更新购物车商品数量
	 * 
	 * @param userId
	 *            -用户Id
	 * @param goodsId
	 *            -商品Id
	 * @param count
	 *            -商品数量
	 * @param status
	 *            -操作状态(0-新增,1-扣減)
	 * @param optionType
	 * @return Map<String, Object>
	 * @add by 2016-04-07
	 */
	Map<String, Object> updateShoppingCartGoodsCount(long userId, String goodsId, String shopId, int count, int status,
			int optionType, int type) throws Exception;

	/**
	 * 根据用户Id/商品Id删除购物车商品
	 * 
	 * @param userId
	 *            -用户Id
	 * @param goodsIds
	 *            -商品Id(多个商品id用,隔开)
	 * @param optionType
	 * @return Map<String, Object>
	 * @add by 2016-04-07
	 */
	Map<String, Object> deleteShoppingCartGoods(long userId, String goodsIds, String shopId, int optionType, int status);
}
