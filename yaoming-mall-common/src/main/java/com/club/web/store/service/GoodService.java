/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.store.vo.GoodDetailsVo;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.ShanguoGoodMsg;
import com.club.web.store.vo.TradeGoodSkuVo;
import com.club.web.store.vo.TradeGoodVo;

/**
 * @Title:
 * @Description: 商品的service层接口
 * @Author:Administrator
 * @Since:2016年3月25日
 * @Version:1.1.0
 */
public interface GoodService {
	void addGood(GoodVo goodVo, List<String> labels, List<String> cargoSkuNames, List<String> cargoSkuIds,
			List<String> marketPriceList, List<String> salePriceList, List<String> supplyPrice, long i,
			List<String> levelIds, HttpServletRequest request) throws Exception;

	void updateGood(TradeGoodVo vo);

	void deleteById(long id);

	Page<Map<String, Object>> queryGoodPage(Page<Map<String, Object>> page);

	Map<String, Object> deleteGoods(String idStr);

	List<TradeGoodSkuVo> selectTradeGoodSkuVo(String goodId);

	Boolean updateUpGoodStatus(String goodId, List<SkuGoodsParam> list, long userId) throws Exception;

	Boolean updateDownGoodStatus(List<String> list, long userId) throws Exception;

	public void editGood(GoodVo goodVo, List<String> labels, List<String> cargoSkuIds, List<String> cargoSkuNames,
			List<String> marketPriceList, List<String> salePriceList, List<String> supplyPrice, long i,
			List<String> levelIds, HttpServletRequest request) throws Exception;

	public Long ifGoodExgist(long cargoId);

	List<GoodListVo> queryGoodList(Map<String, Object> map);

	GoodDetailsVo getGoodDetails(long goodId);

	List<TradeGoodSkuVo> queryGoodSkuList(Long goodId);

	public Map<String, String> updateGoodSkuNums(List<SkuGoodsParam> list, int status) throws Exception;

	/**
	 * 查询出售中的商品
	 * 
	 * @param param
	 * @return List<ShanguoGoodMsg>
	 */
	List<ShanguoGoodMsg> getSaleGoodListSer(Map<String, Object> param);

	/**
	 * 查询已下架的商品
	 *
	 * @param param
	 * @return List<ShanguoGoodMsg>
	 * @add by 2016-05-17
	 */
	List<ShanguoGoodMsg> getUnshelveGoodListSer(Map<String, Object> param);

	/**
	 * 搜索商品列表
	 *
	 * @param goodsName
	 * @param pageSize
	 * @param pageNum
	 * @return List<ShanguoGoodMsg>
	 * @add by 2016-05-17
	 */
	List<ShanguoGoodMsg> getSearchGoodListSer(Map<String, Object> param);

	/**
	 * 查询商品详情
	 *
	 * @param goodId
	 * @return GoodDetailsVo
	 * @add by 2016-05-18
	 */
	GoodDetailsVo getShanguoGoodDetailSer(Long goodId, Long shopId);

	/**
	 * 获取微信端的商品列表
	 * 
	 * @param conditionStr
	 * @return List<ShanguoGoodMsg>
	 */
	List<ShanguoGoodMsg> getWeixinGoodListSer(String conditionStr);

	/**
	 * 商品列表（通用接口：出售中，已下架，查询商品名称）
	 * @return List<ShanguoGoodMsg>
	 */
	List<ShanguoGoodMsg> getGoodListByMap(Map<String, Object> map);
	public List<GoodsColumnVo> getColumnList();
}
