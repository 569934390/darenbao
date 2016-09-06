package com.club.web.store.controller;

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

import com.club.web.common.Constants;
import com.club.web.store.service.ShoppingCartService;
import com.club.web.store.vo.ShoppingCartVo;

/**
 * 购物车-Controller
 * 
 * @author wqh
 * @add by 2016-04-09
 */
@RequestMapping("/mobile")
@Controller
public class ShoppingCartController {
	/**
	 * 引入日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
	/**
	 * 注入库存的service
	 */
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
	 * @return List<ShoppingCartVo>
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/queryCart")
	@ResponseBody
	public List<ShoppingCartVo> queryCartCon(HttpServletResponse response, String userId) {
		List<ShoppingCartVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			list = cart.queryShoppingCartByUserId(user, 0);
		} catch (Exception e) {
			logger.error("查询购物车商品异常<queryCartCon>:", e);
		}
		return list;
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
	public List<ShoppingCartVo> queryStoreCon(HttpServletResponse response, String userId) {
		List<ShoppingCartVo> list = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			list = cart.queryShoppingCartByUserId(user, 1);
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
	public Map<String, Object> addCartCon(String userId, String goodsId,
			@RequestParam(defaultValue = "0") String shopId, int count, HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			result = cart.addShoppingCartByUserId(user, goodsId, shopId, count, 0.00, 0, 0);
		} catch (Exception e) {
			logger.error("加入购物车异常<addCartCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
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
	public Map<String, Object> addStoreCon(HttpServletResponse response, String userId, String goodsId,
			@RequestParam(defaultValue = "") String shopId, @RequestParam(defaultValue = "0") int count) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			result = cart.addShoppingCartByUserId(user, goodsId, shopId, count, 0.00, 1, 0);
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
	 * @param count
	 * @param status
	 *            (0-增加1-减少)
	 * @param response
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/updateCart")
	@ResponseBody
	public Map<String, Object> updateCartCon(String userId, String goodsId, int count, int status,
			HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			result = cart.updateShoppingCartGoodsCount(user, goodsId, "0", count, status, 0, 0);
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
	 * @add by 2016-04-09
	 */
	@RequestMapping("/cart/delCart")
	@ResponseBody
	public Map<String, Object> delCartCon(String userId, String goodsIds, HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			result = cart.deleteShoppingCartGoods(user, goodsIds, "0", 0, 0);
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
	public Map<String, Object> delStoreCon(String userId, String goodsIds, HttpServletResponse response) {
		result = new HashMap<>();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentType("text/json;charset=utf-8");
			long user = Long.valueOf(userId);
			result = cart.deleteShoppingCartGoods(user, goodsIds, "0", 1, 0);
		} catch (Exception e) {
			logger.error("修改购物车商品数量异常<updateCartCon>:", e);
			result.put(Constants.RESULT_CODE, Constants.HANDLER_ERR_CODE);
			result.put(Constants.RESULT_MSG, "操作失败");
		}
		return result;
	}
}
