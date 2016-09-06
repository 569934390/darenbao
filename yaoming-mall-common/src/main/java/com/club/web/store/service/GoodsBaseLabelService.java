package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;

/**   
* @Title: GoodsBaseLabelService.java
* @Package com.club.web.store.service 
* @Description: 商品基础标签Service接口类 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public interface GoodsBaseLabelService {
	
	Page<Map<String, Object>> selectGoodsBaseLabelByLabelName(Page<Map<String, Object>> page, 
			String labelName, String shopYn, HttpServletRequest request);
	
	Map<String, Object> addGoodsBaseLabel(GoodsBaseLabelVo goodsBaseLabelVo, HttpServletRequest request);
	
	Map<String, Object> editGoodsBaseLabel(GoodsBaseLabelVo goodsBaseLabelVo);
	
	Map<String, Object> deleteGoodsBaseLabel(String idStr, String shopFlag);
	
	Map<String, Object> changeStatus(String idStr, String status);
	
	List<GoodsBaseLabelVo> selectGoodsBaseLabelListByLabelName(Map<String, Object> page, 
			String labelName, String shopYn, HttpServletRequest request);

	List<GoodsBaseLabelVo> findListAll();
}
