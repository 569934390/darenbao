package com.club.web.store.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.web.store.vo.GoodsColumnVo;

/**   
* @Title: ShanGuoGoodsColumnService.java
* @Package com.club.web.store.service 
* @Description: 山国商品基础栏目Service接口类 
* @author hqLin   
* @date 2016/05/16
* @version V1.0   
*/
public interface ShanGuoGoodsColumnService {
	
	Map<String, Object> addGoodsColumn(GoodsColumnVo goodsColumnVo, HttpServletRequest request);
	
	Map<String, Object> editGoodsColumn(GoodsColumnVo goodsColumnVo);
}