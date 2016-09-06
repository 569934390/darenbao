package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.vo.CargoSkuItemVo;
import com.club.web.store.domain.ShoppingCartDo;
import com.club.web.store.domain.repository.ShoppingCartRepository;
import com.club.web.store.service.ShoppingCartService;
import com.club.web.store.vo.ShoppingCartVo;

/**
 * 购物车-service实现
 * 
 * @author wqh
 * @add by 2016-04-07
 */
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
	/**
	 * 日志操作
	 */
	private Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);
	/**
	 * 注入购物车仓库
	 */
	@Autowired
	ShoppingCartRepository repository;
	@Autowired
	StockManagerRepository stockRepository;
	/**
	 * 操作返回结果
	 */
	private Map<String, Object> result;

	/**
	 * 根据用户Id查询购物车商品
	 * 
	 * @param userId
	 *            -用户Id
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-07
	 */
	@Override
	public List<ShoppingCartVo> queryShoppingCartByUserId(long userId, int optionType) {
		List<ShoppingCartVo> list = repository.queryShoppingCartByUserId(userId, optionType);
		if (0 == optionType) {
			setCargoSkuSpecList(list);
		}
		return list;
	}

	/**
	 * 根据用户Id查询购物车商品-三国
	 * 
	 * @param userId
	 *            -用户Id
	 * @param optionType
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-07
	 */
	@Override
	public List<ShoppingCartVo> queryShoppingCartByUserId(long userId, long shopId, int optionType) {
		List<ShoppingCartVo> list = repository.queryShoppingCartByUserId(userId, shopId, optionType);
		if (0 == optionType) {
			setCargoSkuSpecList(list);
		}
		return list;
	}

	/**
	 * 设置对象的规格值
	 * 
	 * @param listStock
	 * @add by 2016-04-11
	 */
	private <T> void setCargoSkuSpecList(List<T> t) {
		List<CargoSkuItemVo> list = null;
		if (t != null && t.stream().count() > 0) {
			for (T vo : t) {
				try {
					if (vo instanceof ShoppingCartVo) {
						ShoppingCartVo cart = (ShoppingCartVo) vo;
						list = stockRepository.queryGoodsSpecList(cart.getSkuId());
						if (list != null && list.size() > 0) {
							StringBuffer str = new StringBuffer();
							for (int i = 0; i < list.stream().count(); i++) {
								str.append(list.get(i).getType() + ":" + list.get(i).getSkuName());
								if (i != list.stream().count() - 1) {
									str.append(",");
								}
							}
							cart.setSkuItem(str.toString());
						}
					}
				} catch (Exception e) {
					logger.error("设置对象的规格值异常<setCargoSkuSpecList>:", e);
				}
			}
		}

	}

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
	@Override
	public Map<String, Object> addShoppingCartByUserId(long userId, String goodsId, String shopId, int count,
			double prize, int optionType, int status) throws Exception {
		result = new HashMap<>();
		ShoppingCartDo cart = null;
		long goodId = 0;
		long storeId = 0;
		if (StringUtils.isNotEmpty(goodsId)) {
			if (StringUtils.isNotEmpty(shopId)) {
				storeId = Long.valueOf(shopId);
			}
			goodId = Long.valueOf(goodsId);
			if (count <= 0) {
				count = 1;
			}
			cart = repository.createShoppingCartObj(userId, goodId, storeId, count, prize, optionType, status);
			if (cart != null) {
				if (cart.getFlag() == 1) {
					cart.save();
				} else {
					cart.update(count, 0);
				}
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "对象为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "添加商品id为空");
		}
		return result;
	}

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
	@Override
	public Map<String, Object> updateShoppingCartGoodsCount(long userId, String goodsId, String shopId, int count,
			int status, int optionType, int type) throws Exception {
		result = new HashMap<>();
		ShoppingCartDo cart = null;
		long goodId = Long.valueOf(goodsId);
		long storeId = Long.valueOf(shopId);
		if (count > 0) {
//			if (status == 0 || status == 1) {
				cart = repository.queryCartObjByCondition(userId, goodId, storeId, optionType, type);
				if (cart != null) {
//					if (status == 1) {
//						if (count > cart.getGoodsCount()) {
//							count = cart.getGoodsCount();
//						}
//					}
					cart.update(count, status);
					result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
					result.put(Constants.RESULT_MSG, "操作成功");
				} else {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "操作对象不存在");
				}
//			} else {
//				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
//				result.put(Constants.RESULT_MSG, "操作类型不存在");
//			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "修改数量要大于0");
		}
		return result;
	}

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
	@Override
	public Map<String, Object> deleteShoppingCartGoods(long userId, String goodsIds, String shopId, int optionType,
			int status) {
		result = new HashMap<>();
		List<Long> listIds = null;
		long storeId = 0;
		if (StringUtils.isNotEmpty(goodsIds)) {
			String[] arr = goodsIds.split(",");
			if (arr != null && arr.length > 0) {
				listIds = new ArrayList<>();
				for (String str : arr) {
					listIds.add(Long.valueOf(str));
				}
			}
		}
		if (StringUtils.isNotEmpty(shopId)) {
			storeId = Long.valueOf(shopId);
		}
		if (listIds != null && listIds.stream().count() > 0) {
			repository.deleteShoppingCartGoods(userId, listIds, storeId, optionType, status);
			result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
			result.put(Constants.RESULT_MSG, "操作成功");
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
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
	public int getCartCountSer(long userId, long shopId, int optionType){
		int count=repository.getCartCount(userId, shopId, optionType);
		return count;
	}
}
