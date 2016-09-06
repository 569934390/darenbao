package com.club.web.store.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.GoodsBaseLabelMapper;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;

/**   
* @Title: GoodsBaseLabelExtendMapper.java
* @Package com.club.web.store.dao.extend
* @Description: 商品基础标签dao扩展接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

public interface GoodsBaseLabelExtendMapper extends GoodsBaseLabelMapper{
	
   List<GoodsBaseLabelVo> selectGoodsBaseLabelByLabelName(Map<String, Object> map);
   
   Long queryGoodsBaseLabelCountPage(Map<String, Object> map);
   
   int updateStatusById(@Param("id") Long id, @Param("status") String status);
   
   List<GoodsBaseLabelVo> selectGoodsBaseLabelListByLabelName(Map<String, Object> map);
   
   List<GoodsBaseLabelVo> selectGoodsBaseLabelListByGoodId(@Param("goodId") Long goodId);

   List<GoodsBaseLabelVo> findListAll();
   
   
}