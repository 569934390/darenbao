package com.club.web.store.service;

import java.util.List;

import com.club.web.store.vo.ClassifyVO;
import com.club.web.store.vo.DiscountGoodsVO;
import com.club.web.store.vo.GoodsCommentVO;
import com.club.web.store.vo.GoodsDetailVO;
import com.club.web.store.vo.GoodsSimpleVO;
import com.club.web.store.vo.OrderSimpleVO;

public interface StoreService {
	public List<ClassifyVO> getClassifyList();
	public DiscountGoodsVO getDiscountGoods();
	public List<GoodsSimpleVO> getRecommendGoods();
	public List<GoodsSimpleVO> getHotGoods();
	public List<GoodsSimpleVO> getGoodsList(long classifyid, int page);
	public GoodsDetailVO getGoodsInfo(long goodsId);
	public List<OrderSimpleVO> getOrders(int orderStatus, int page);
	public String getQrCode(String phone);
	public GoodsCommentVO getComment(long goodsId);
}
