package com.club.web.store.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.club.web.store.service.StoreData;
import com.club.web.store.service.StoreService;
import com.club.web.store.vo.ClassifyVO;
import com.club.web.store.vo.CommentVO;
import com.club.web.store.vo.DiscountGoodsVO;
import com.club.web.store.vo.GoodsCommentVO;
import com.club.web.store.vo.GoodsDetailVO;
import com.club.web.store.vo.GoodsSimpleVO;
import com.club.web.store.vo.OrderSimpleVO;

@Service
@PropertySource("classpath:/config/qrcode.properties")
public class StoreServiceImpl implements StoreService{

	public static final int RECOMMEND_GOODS_COUNT = 2;
	public static final int HOT_GOODS_COUNT = 2;
	public static final int DISCOUNT_GOODS_COUNT = 3;
	public static final int PAGE_SIZE = 4;
	@Value("${index_path}")
	private String indexPath;
	
	@Override
	public List<ClassifyVO> getClassifyList() {
		List<ClassifyVO> result = new ArrayList<ClassifyVO>();
		int i = 0;
		for (ClassifyVO classifyVO : StoreData.classifyMap.values())
			if(i++<8)
				result.add(classifyVO);
			else
				break;
		return result;
	}

	@Override
	public DiscountGoodsVO getDiscountGoods() {
		if(StoreData.discountEndTime.getTime()<System.currentTimeMillis())
			StoreData.discountEndTime = new Date(System.currentTimeMillis()+(long)(Math.random()*30*60*1000)+60*60*1000);
    	String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(StoreData.discountEndTime); 
    	Collection<GoodsDetailVO> goodscollections = StoreData.goodsMap.values();
    	List<GoodsSimpleVO> list = new ArrayList<GoodsSimpleVO>();
    	for (GoodsDetailVO gdvo : goodscollections) {
			if(gdvo.isDiscountGoods())
				list.add(gdvo.getSimpleData());
			if(list.size()>=DISCOUNT_GOODS_COUNT)
				break;
    	}
    	DiscountGoodsVO dgvo = new DiscountGoodsVO(time, list);
		return dgvo;
	}

	@Override
	public List<GoodsSimpleVO> getRecommendGoods() {   
		Collection<GoodsDetailVO> goodscollections = StoreData.goodsMap.values();
		List<GoodsSimpleVO> list = new ArrayList<GoodsSimpleVO>();
		for (GoodsDetailVO gdvo : goodscollections) {
			if(gdvo.isRecommend())
				list.add(gdvo.getSimpleData());
			if(list.size()>=RECOMMEND_GOODS_COUNT)
				break;
		}
		return list;
	}

	@Override
	public List<GoodsSimpleVO> getHotGoods() {
		Collection<GoodsDetailVO> goodscollections = StoreData.goodsMap.values();
		List<GoodsSimpleVO> list = new ArrayList<GoodsSimpleVO>();
		for (GoodsDetailVO gdvo : goodscollections) {
			if(gdvo.isHot())
				list.add(gdvo.getSimpleData());
			if(list.size()>=HOT_GOODS_COUNT)
				break;
		}
		return list;
	}

	@Override
	public List<GoodsSimpleVO> getGoodsList(long classifyid, int page) {
		int index = 0;
		int pageStart = (page-1) * PAGE_SIZE;
		Collection<GoodsDetailVO> goodscollections = StoreData.goodsMap.values();
		List<GoodsSimpleVO> list = new ArrayList<GoodsSimpleVO>();
		for (GoodsDetailVO gdvo : goodscollections) 
			if(gdvo!=null && (classifyid==0 || gdvo.getClassify().getClassifyId()==classifyid)){
				if(index++ >= pageStart)
					list.add(gdvo.getSimpleData());
				if(list.size()>=PAGE_SIZE)
					break;
			}
		return list;
	}

	@Override
	public GoodsDetailVO getGoodsInfo(long goodsId) {
		return StoreData.goodsMap.get(goodsId);
	}

	@Override
	public List<OrderSimpleVO> getOrders(int orderStatus, int page) {
		int index = 0;
		int pageStart = (page-1) * PAGE_SIZE;
		Collection<OrderSimpleVO> ordercollections = StoreData.orderMap.values();
		List<OrderSimpleVO> list = new ArrayList<OrderSimpleVO>();
		for (OrderSimpleVO osvo : ordercollections) 
			if(osvo!=null && (orderStatus==0 || osvo.getStatusCode()==orderStatus)){
				if(index++ >= pageStart)
					list.add(osvo);
				if(list.size()>=PAGE_SIZE)
					break;
			}
		return list;
	}

	@Override
	public String getQrCode(String phone) {
		return indexPath+"?phone="+phone;
	}

	@Override
	public GoodsCommentVO getComment(long goodsId) {
		GoodsCommentVO vo = new GoodsCommentVO();
		List<CommentVO> list = StoreData.commentList;
		for (CommentVO cvo : list)
			cvo.setGoodsId(goodsId);
		vo.setList(list);
		vo.setRate(StoreData.goodsMap.get(goodsId).getRate());
		return vo;
	}

}
