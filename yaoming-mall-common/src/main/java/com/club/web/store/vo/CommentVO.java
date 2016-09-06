package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.club.web.store.service.StoreData;

public class CommentVO {
	
	private long id;
	private String headImage;
	private String name;
	private BigDecimal rate;
	private String datetime;
	private String comment;
	private long goodsId;
	public CommentVO(long id, String headImage, String name, BigDecimal rate, String datetime, String comment) {
		this.id = id;
		this.headImage = headImage;
		this.name = name;
		this.rate = rate;
		this.datetime = datetime;
		this.comment = comment;
	}
	public long getId() {
		return id;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	public String getHeadImage() {
		return headImage;
	}
	public String getName() {
		return name;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public String getDatetime() {
		return datetime;
	}
	public String getComment() {
		return comment;
	}
	
	public List<Map<String, Object>> getSpecs() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<GoodsSpecVO> list = StoreData.goodsMap.get(goodsId).getSpecs();
		for (GoodsSpecVO goodsSpecVO : list) {
			if(goodsSpecVO!=null && goodsSpecVO.getDataList().size()>0){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", goodsSpecVO.getName());
				map.put("value", goodsSpecVO.getDataList().get(0));
				result.add(map);
			}
			
		}
		return result;
	}
}
