package com.club.web.store.domain.repository;

import java.util.List;

import com.club.web.store.domain.ShoppingCartDo;
import com.club.web.store.vo.ShoppingCartVo;

/**
 * 购物车仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-07
 */
public interface ShoppingCartRepository {
	/**
	 * 根据用户Id查询购物车对象
	 * 
	 * @param userId
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-11
	 */
	List<ShoppingCartVo> queryShoppingCartByUserId(long userId, int optionType);

	/**
	 * 根据用户Id查询购物车对象
	 * 
	 * @param userId
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-11
	 */
	List<ShoppingCartVo> queryShoppingCartByUserId(long userId, long shopId, int optionType);

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-07
	 */
	<T> void save(T t) throws Exception;

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-07
	 */
	<T> void update(T t) throws Exception;

	/**
	 * 创建购物车商品对象信息
	 * 
	 * @param userId
	 *            -用户Id
	 * @param goodId
	 *            -商品Id
	 * @param storeId
	 *            -商店Id
	 * @param count
	 *            -商品数量
	 * @param prize
	 *            -商品价格
	 * @param optionType
	 * @param status
	 * @return ShoppingCartDo
	 * @add by 2016-04-07
	 */
	ShoppingCartDo createShoppingCartObj(long userId, long goodId, long storeId, int count, double prize,
			int optionType, int status);

	/**
	 * 根据用户Id和商品Id查询对象
	 * 
	 * @param userId
	 *            -用户Id
	 * @param goodId
	 *            -商品Id
	 * @param storeId
	 * @param optionType
	 * @param status
	 * @return ShoppingCartDo
	 * @add by 2016-04-07
	 */
	ShoppingCartDo queryCartObjByCondition(long userId, long goodId, long storeId, int optionType, int status);

	/**
	 * 根据用户Id和商品Id删除对象
	 * 
	 * @param userId
	 *            -用户Id
	 * @param goodsId
	 *            -商品Id
	 * @param optionType
	 * @return void
	 * @add by 2016-04-07
	 */
	void deleteShoppingCartGoods(long userId, List<Long> goodsIds, long shopId, int optionType, int status);

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
	int getCartCount(long userId, long shopId, int optionType);
}
