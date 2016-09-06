/**
 * 
 */
package com.club.web.store.dao.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.store.dao.base.po.ShoppingCart;
import com.club.web.store.dao.extend.ShoppingCartExtendMapper;
import com.club.web.store.domain.ShoppingCartDo;
import com.club.web.store.domain.repository.ShoppingCartRepository;
import com.club.web.store.vo.ShoppingCartVo;
import com.club.web.util.IdGenerator;

/**
 * 购物车仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-07
 */
@Repository
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
	/**
	 * 注入购物车dao
	 */
	@Autowired
	ShoppingCartExtendMapper cartDao;

	/**
	 * 根据用户Id查询购物车对象
	 * 
	 * @param userId
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-11
	 */
	@Override
	public List<ShoppingCartVo> queryShoppingCartByUserId(long userId, int optionType) {
		List<ShoppingCartVo> list = null;
		if (optionType == 0) {
			list = cartDao.queryShoppingCartByUserId(userId, 0, optionType, 0);
		} else {
			list = cartDao.queryShoppingStoreByUserId(userId, 0, optionType, 0);
		}
		return list;
	}

	/**
	 * 根据用户Id查询购物车对象
	 * 
	 * @param userId
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-11
	 */
	@Override
	public List<ShoppingCartVo> queryShoppingCartByUserId(long userId, long shopId, int optionType) {
		List<ShoppingCartVo> list = null;
		if (optionType == 0) {
			list = cartDao.queryShoppingCartByUserId(userId, shopId, optionType, 1);
		} else {
			list = cartDao.queryShoppingStoreByUserId(userId, shopId, optionType, 1);
		}
		return list;
	}

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-07
	 */
	@Override
	public <T> void save(T t) throws Exception {
		if (t != null) {
			if (t instanceof ShoppingCartDo) {
				ShoppingCartDo cart = (ShoppingCartDo) t;
				ShoppingCart po = new ShoppingCart();
				BeanUtils.copyProperties(po, cart);
				cartDao.insert(po);
			} else {
				throw new Exception("不存在操作的对象实例");
			}
		} else {
			throw new NullPointerException("对象为空");
		}

	}

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-07
	 */
	@Override
	public <T> void update(T t) throws Exception {
		if (t != null) {
			if (t instanceof ShoppingCartDo) {
				ShoppingCartDo cart = (ShoppingCartDo) t;
				ShoppingCart po = new ShoppingCart();
				BeanUtils.copyProperties(po, cart);
				cartDao.updateByPrimaryKey(po);
			} else {
				throw new Exception("不存在操作的对象实例");
			}
		} else {
			throw new NullPointerException("对象为空");
		}
	}

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
	public ShoppingCartDo createShoppingCartObj(long userId, long goodId, long storeId, int count, double prize,
			int optionType, int status) {
		ShoppingCartDo cart = queryCartObjByCondition(userId, goodId, storeId, optionType, status);
		if (cart == null) {
			cart = new ShoppingCartDo();
			cart.setId(IdGenerator.getDefault().nextId());
			cart.setUserId(userId);
			cart.setGoodsId(goodId);
			cart.setShopId(storeId);
			cart.setGoodsCount(count);
			cart.setGoodsPrize(new BigDecimal(prize));
			cart.setUpdateDate(new Date());
			cart.setCreateDate(new Date());
			cart.setFlag(1);
			cart.setOpertionType(optionType);
		} else {
			cart.setFlag(0);
		}
		return cart;
	}

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
	@Override
	public ShoppingCartDo queryCartObjByCondition(long userId, long goodId, long storeId, int optionType, int status) {
		ShoppingCartDo cart = cartDao.queryCartObjByCondition(userId, goodId, storeId, optionType, status);
		return cart;
	}

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
	@Override
	public void deleteShoppingCartGoods(long userId, List<Long> goodsIds, long shopId, int optionType, int status) {
		cartDao.deleteShoppingCartGoods(userId, goodsIds, shopId, optionType, status);
	}

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
	@Override
	public int getCartCount(long userId, long shopId, int optionType) {
		int count = cartDao.getCartCount(userId, shopId, optionType);
		return count;
	}
}
