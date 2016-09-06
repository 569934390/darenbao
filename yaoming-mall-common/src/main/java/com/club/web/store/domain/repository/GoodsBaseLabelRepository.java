package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.GoodsBaseLabelDo;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;

/**   
* @Title: GoodsBaseLabelRepository.java
* @Package com.club.web.store.domain.repository
* @Description: 商品基础标签domain接口类 
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/
public interface GoodsBaseLabelRepository {
	
	List<GoodsBaseLabelVo> selectGoodsBaseLabelByLabelName(Map<String, Object> map);
	
	int addGoodsBaseLabel(GoodsBaseLabelDo goodsBaseLabelDo);
	
	int editGoodsBaseLabel(GoodsBaseLabelDo goodsBaseLabelDo);
	
	int deleteGoodsBaseLabel(Long id);
	
	Long queryGoodsBaseLabelCountPage(Map<String, Object> map);
	
	GoodsBaseLabelDo create();
	
	GoodsBaseLabelDo voChangeDo(GoodsBaseLabelVo goodsBaseLabelVo);
	
	int updateStatusById(Long id, String status);
	
	List<GoodsBaseLabelVo> selectGoodsBaseLabelListByLabelName(Map<String, Object> map);
	
	GoodsBaseLabelVo selectGoodsBaseLabelById(Long id);
	
	public List<GoodsBaseLabelVo> selectGoodsBaseLabelListByGoodId(long goodId);

	List<GoodsBaseLabelVo> findListAll();
}
