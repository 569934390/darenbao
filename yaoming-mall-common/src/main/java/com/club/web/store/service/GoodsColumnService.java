package com.club.web.store.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.RuleSourceVo;

/**   
* @Title: GoodsColumnService.java
* @Package com.club.web.store.service 
* @Description: 商品基础栏目Service接口类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public interface GoodsColumnService {
	
	Page<Map<String, Object>> selectGoodsColumnByColumnName(Page<Map<String, Object>> page, 
			String columnName, String shopYn, HttpServletRequest request);
	
	Map<String, Object> addGoodsColumn(GoodsColumnVo goodsColumnVo, HttpServletRequest request);
	
	Map<String, Object> editGoodsColumn(GoodsColumnVo goodsColumnVo);
	
	Map<String, Object> deleteGoodsColumn(String idStr);
	
	Map<String, Object> updateStatusForGoodsColumnById(String idStr, String status);
	
	List<GoodsColumnVo> selectGoodsColumnListByShopId(HttpServletRequest request);
	
	List<RuleSourceVo> selectRuleSourceList(int ruleType);
	
	List<Map<String, Object>> getGoodsAndCloumn(Long shopId) throws SQLException;
	
	Map<String, Object> selectGoodsByColumnId(Long columnsId,int start,int limit);
	
	List<Map<String, Object>> selectColumnsGood();

	Map<String, Object> goodsColumnService();
}