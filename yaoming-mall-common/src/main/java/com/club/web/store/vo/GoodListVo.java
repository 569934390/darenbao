package com.club.web.store.vo;

import java.util.Date;

/**   
* @Title: GoodListVo.java
* @Package com.club.web.store.vo 
* @Description: 商品列表VO 
* @author hqLin   
* @date 2016/04/18
* @version V1.0   
*/
public class GoodListVo { 
	private String id;
    private String headstoreId;
    private String cargoId;
    private String name;
    private String describe;
    private Long sort;
    private Integer type;
    private Long category;
    private String post;
    private Double postFee;
    private Long creator;
    private Date createTime;
    private Integer status;
    private Integer saleNum;
	
	private String columnId;
	private String columnName;
	private String columnPicture;
	private String showPicture;
	private String score;
	private Double marketPrice;
	private Double salePrice;
	private String imgSquare;//商品图片----正方形
	private String imgRectangle;//商品图片----长方形
	private String imgLarge;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadstoreId() {
		return headstoreId;
	}

	public void setHeadstoreId(String headstoreId) {
		this.headstoreId = headstoreId;
	}

	public String getCargoId() {
		return cargoId;
	}

	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Double getPostFee() {
		return postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getShowPicture() {
		return showPicture;
	}

	public void setShowPicture(String showPicture) {
		this.showPicture = showPicture;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getColumnPicture() {
		return columnPicture;
	}

	public void setColumnPicture(String columnPicture) {
		this.columnPicture = columnPicture;
	}

	public String getImgSquare() {
		return imgSquare;
	}

	public void setImgSquare(String imgSquare) {
		this.imgSquare = imgSquare;
	}

	public String getImgRectangle() {
		return imgRectangle;
	}

	public void setImgRectangle(String imgRectangle) {
		this.imgRectangle = imgRectangle;
	}

	public String getImgLarge() {
		return imgLarge;
	}

	public void setImgLarge(String imgLarge) {
		this.imgLarge = imgLarge;
	}
}