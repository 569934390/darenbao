package com.club.web.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.service.ShoppingCartService;
import com.club.web.store.vo.ShoppingCartVo;

/**
 * 购物车-Controller
 * 
 * @author wqh
 * @add by 2016-04-09
 */
@RequestMapping("/weixin")
@Controller
public class ShoppingCartWxController {
	private Logger logger = LoggerFactory.getLogger(ShoppingCartWxController.class);
	@Autowired
	ShoppingCartService cart;
	/**
	 * 操作结果返回信息
	 */
	private Map<String, Object> result;

	/**
	 * 查询购物车商品
	 * 
	 * @param response
	 * @param userId
	 * @param shopId
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/queryCart")
	@ResponseBody
	public List<ShoppingCartVo> queryCartCon(HttpServletResponse response, String userId, String shopId) {
		List<ShoppingCartVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			long shop = Long.valueOf(shopId);
			list = cart.queryShoppingCartByUserId(user, shop, 0);
		} catch (Exception e) {
			logger.error("查询购物车商品异常<queryCartCon>:", e);
		}
		return list;
	}

	/**
	 * 查询购物车商品数量
	 * 
	 * @param response
	 * @param userId
	 * @param shopId
	 * @return int
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/getCartCount")
	@ResponseBody
	public int queryCartCountCon(HttpServletResponse response, String userId, String shopId) {
		int count = 0;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			long shop = Long.valueOf(shopId);
			count = cart.getCartCountSer(user, shop, 0);
		} catch (Exception e) {
			logger.error("查询购物车商品数量异常<queryCartCountCon>:", e);
		}
		return count;
	}

	/**
	 * 查询收藏商品
	 * 
	 * @param response
	 * @param userId
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-19
	 */
	@RequestMapping("/store/queryStore")
	@ResponseBody
	public List<ShoppingCartVo> queryStoreCon(HttpServletResponse response, String userId, String shopId) {
		List<ShoppingCartVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			long shop = Long.valueOf(shopId);
			list = cart.queryShoppingCartByUserId(user, shop, 1);
		} catch (Exception e) {
			logger.error("查询收藏商品异常<queryStoreCon>:", e);
		}
		return list;
	}

	/**
	 * 加入购物车商品
	 * 
	 * @param userId
	 * @param goodsId
	 * @param shopId
	 * @param count
	 * @param response
	 * @return Map<String, Object>
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/addCart")
	@ResponseBody
	public Map<String, Object> addCartCon(String userId, String goodsId, String shopId, int count,
			HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(goodsId) && StringUtils.isNotEmpty(shopId)) {
				long user = Long.valueOf(userId);
				result = cart.addShoppingCartByUserId(user, goodsId, shopId, count, 0.00, 0, 1);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("加入购物车异常<addCartCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 加入商品到收藏
	 * 
	 * @param userId
	 * @param goodsId
	 * @param shopId
	 * @param count
	 * @param response
	 * @return Map<String, Object>
	 * @add by 2016-04-09
	 */
	@RequestMapping("/store/addStore")
	@ResponseBody
	public Map<String, Object> addStoreCon(HttpServletResponse response, String userId, String goodsId, String shopId,
			@RequestParam(defaultValue = "0") int count) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(goodsId) && StringUtils.isNotEmpty(shopId)) {
				long user = Long.valueOf(userId);
				result = cart.addShoppingCartByUserId(user, goodsId, shopId, count, 0.00, 1, 1);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("加入商品到收藏异常<addStoreCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 修改购物车商品数量
	 * 
	 * @param userId
	 * @param goodsId
	 * @param shopId
	 * @param count
	 * @param status
	 *            (0-增加1-减少)
	 * @param response
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/updateCart")
	@ResponseBody
	public Map<String, Object> updateCartCon(String userId, String goodsId, String shopId, int count, int status,
			HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(goodsId) && StringUtils.isNotEmpty(shopId)) {
				long user = Long.valueOf(userId);
				result = cart.updateShoppingCartGoodsCount(user, goodsId, shopId, count, status, 0, 1);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改购物车商品数量异常<updateCartCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 删除购物车商品
	 * 
	 * @param userId
	 * @param goodsIds
	 * @param shopId
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/delCart")
	@ResponseBody
	public Map<String, Object> delCartCon(String userId, String goodsIds, String shopId, HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(goodsIds) && StringUtils.isNotEmpty(shopId)) {
				long user = Long.valueOf(userId);
				result = cart.deleteShoppingCartGoods(user, goodsIds, shopId, 0, 1);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改购物车商品数量异常<updateCartCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}

	/**
	 * 删除收藏商品
	 * 
	 * @param userId
	 * @param goodsIds
	 * @add by 2016-04-09
	 */
	@RequestMapping("/store/delStore")
	@ResponseBody
	public Map<String, Object> delStoreCon(String userId, String goodsIds, String shopId, HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(goodsIds) && StringUtils.isNotEmpty(shopId)) {
				long user = Long.valueOf(userId);
				result = cart.deleteShoppingCartGoods(user, goodsIds, shopId, 1, 1);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改购物车商品数量异常<updateCartCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}
}
