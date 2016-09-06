package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.GoodsColumnDo;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodsColumnIndexVo;
import com.club.web.store.vo.GoodsColumnVo;

/**   
* @Title: GoodsColumnRepository.java
* @Package com.club.web.store.domain.repository
* @Description: 商品基础栏目domain接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/
public interface GoodsColumnRepository {
	
	List<GoodsColumnVo> selectGoodsColumnByColumnName(Map<String, Object> map);
	
	int addGoodsColumn(GoodsColumnDo goodsColumnDo);
	
	int editGoodsColumn(GoodsColumnDo goodsColumnDo);
	
	int deleteGoodsColumn(Long id);
	
	Long queryGoodsColumnCountPage(Map<String, Object> map);
	
	GoodsColumnDo create();
	
	GoodsColumnDo voChangeDo(GoodsColumnVo goodsColumnVo);
	
	int updateStatusForGoodsColumnById(Long id, String status);
	
	List<GoodListVo> selectColumnAllGoods(Map<String, Object> map);
	
	List<GoodListVo> selectGoodsByColumnId(Map<String, Object> map);
	
	List<GoodListVo> selectColumnsGood(Map<String, Object> map);

	List<GoodsColumnIndexVo> selectNoRuleGoodsColumn();

	List<GoodsColumnIndexVo> selectRuleGoodsColumn();
}