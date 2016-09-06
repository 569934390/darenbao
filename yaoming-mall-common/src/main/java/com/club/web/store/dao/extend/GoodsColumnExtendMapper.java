package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.GoodsColumnMapper;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodsColumnIndexVo;
import com.club.web.store.vo.GoodsColumnVo;

/**   
* @Title: GoodsColumnExtendMapper.java
* @Package com.club.web.store.dao.extend
* @Description: 商品基础栏目dao扩展接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface GoodsColumnExtendMapper extends GoodsColumnMapper{
	
   List<GoodsColumnVo> selectGoodsColumnByColumnName(Map<String, Object> map);
   
   Long queryGoodsColumnCountPage(Map<String, Object> map);
   
   int updateStatusForGoodsColumnById(@Param("id") Long id, @Param("status") String status);
   
   List<GoodListVo> selectColumnAllGoods(Map<String, Object> map);
   
   List<GoodListVo> selectGoodsByColumnId(Map<String, Object> map);
   
   List<GoodListVo> selectColumnsGood(Map<String, Object> map);

   List<GoodsColumnIndexVo> selectNoRuleGoodsColumn();
	
   List<GoodsColumnIndexVo> selectRuleGoodsColumn();
   
   List<GoodsColumnVo> selectAllGoodsColumn();
}