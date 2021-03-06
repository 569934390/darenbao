package com.club.web.store.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.club.web.stock.vo.ImageGroupVo;

/**
 * @Title: GoodDetailsVo.java
 * @Package com.club.web.store.vo
 * @Description: 商品详情VO
 * @author hqLin
 * @date 2016/04/19
 * @version V1.0
 */
public class GoodDetailsVo {

	private String tradeGoodId; // 商品ID
	private List<TradeGoodSkuVo> goodSkuList; // 商品SKU
	private String name; // 商品名称
	private String post; // 包邮
	private BigDecimal marketPrice; // 市场价
	private BigDecimal salePrice; // 零售价
	private BigDecimal supplyPrice; // 零售价
	private int saleNum; // 销量
	private int kucun; // 库存
	private int score; // 评价平均分
	private String smallImage; // 头像
	private ImageGroupVo showImages; // 展示图片组
	private ImageGroupVo detailImages; // 详情图片组
	private List<GoodEvaluationVo> goodEvaluationList; // 商品评价
	private List<Map<String, Object>> skuTypeList; // 商品SKU类型列表
	private int status;
	private Date startDate;
	private Date endDate;
	private String columnName;
	private int storeNum;
	private String postId; // 配送规则
	private String cargoNo;//货品编号

	public String getTradeGoodId() {
		return tradeGoodId;
	}

	public void setTradeGoodId(String tradeGoodId) {
		this.tradeGoodId = tradeGoodId;
	}

	public List<TradeGoodSkuVo> getGoodSkuList() {
		return goodSkuList;
	}

	public void setGoodSkuList(List<TradeGoodSkuVo> goodSkuList) {
		this.goodSkuList = goodSkuList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public int getKucun() {
		return kucun;
	}

	public void setKucun(int kucun) {
		this.kucun = kucun;
	}

	public ImageGroupVo getShowImages() {
		return showImages;
	}

	public void setShowImages(ImageGroupVo showImages) {
		this.showImages = showImages;
	}

	public ImageGroupVo getDetailImages() {
		return detailImages;
	}

	public void setDetailImages(ImageGroupVo detailImages) {
		this.detailImages = detailImages;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public List<GoodEvaluationVo> getGoodEvaluationList() {
		return goodEvaluationList;
	}

	public void setGoodEvaluationList(List<GoodEvaluationVo> goodEvaluationList) {
		this.goodEvaluationList = goodEvaluationList;
	}

	public List<Map<String, Object>> getSkuTypeList() {
		return skuTypeList;
	}

	public void setSkuTypeList(List<Map<String, Object>> skuTypeList) {
		this.skuTypeList = skuTypeList;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
}