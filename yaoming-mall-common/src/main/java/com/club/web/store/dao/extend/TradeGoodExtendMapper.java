package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.TradeGoodMapper;
import com.club.web.store.dao.base.po.TradeGood;
import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.ShanguoGoodMsg;

public interface TradeGoodExtendMapper extends TradeGoodMapper {

	List<GoodVo> queryGoodPage(Map<String, Object> map);

	Long queryGoodCountPage(Map<String, Object> map);

	void updateStatus(Map<String, Object> map);
	
	void updateSaleNumById(GoodUpDo  goodUpDo);

	Long selectGoodNumsByCargoId(@Param("cargoId") long cargoId);

	List<GoodListVo> queryGoodList(Map<String, Object> map);

	List<ShanguoGoodMsg> getGoodListByMap(Map<String, Object> map);
	
	List<ShanguoGoodMsg> getSaleGoodList(Map<String, Object> map);

	List<ShanguoGoodMsg> getUnshelveGoodList(Map<String, Object> map);

	List<ShanguoGoodMsg> getSearchGoodList(Map<String, Object> map);

	List<GoodVo> selectGoodByCategory(@Param("category") Long category);

	List<ShanguoGoodMsg> getWeixinGoodList(Map<String, Object> map);

	ShanguoGoodMsg getWeixinGoodObj(Map<String, Object> map);

	GoodVo findGoodBySkuId(@Param("skuId")Long skuId);
}