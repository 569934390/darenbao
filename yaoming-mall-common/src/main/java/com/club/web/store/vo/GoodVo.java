/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@Title:
 *@Description:
 *@Author:Czj
 *@Since:2016��3��28��
 *@Version:1.1.0
 */
public class GoodVo {
    
	private String tradeGoodId;
	private String goodSkuId;
	private String categoryId;
	private String statusName;
	private String cargoBrand;
	private String cargoType;
	private List<GoodsBaseLabelVo> goodLabelsList;
	private List<TradeGoodSkuVo> goodSkuList;
    private List<GoodsColumnVo> goodColumnList;
	private String firstMarketPrice;
	private String firstSalePrice;
	private String cargoName;
	private String cargo;
	private List<StoreLevelVo> storeLevelList;
	private String imgSquare;
    private String imgRectangle;
    private String imgLarge;
    private String squareUrl;
    private String rectangleUrl;  
    private String largeUrl;
    private Date beginTime;
    private Date endTime;
    
    private String timeRule;
    
    private String beginTimeStr;
    
    private String endTimeStr;
    
    private String postid;
    
    private String saleNum;
    
    
    
	public String getSaleNum() {
		return saleNum;
	}


	public void setSaleNum(String saleNum) {
		this.saleNum = saleNum;
	}


	public String getPostid() {
		return postid;
	}


	public void setPostid(String postid) {
		this.postid = postid;
	}


	public List<GoodsColumnVo> getGoodColumnList() {
		return goodColumnList;
	}


	public void setGoodColumnList(List<GoodsColumnVo> goodColumnList) {
		this.goodColumnList = goodColumnList;
	}

    

	public String getLargeUrl() {
		return largeUrl;
	}


	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}


	public String getBeginTimeStr() {
		return beginTimeStr;
	}


	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}


	public String getEndTimeStr() {
		return endTimeStr;
	}


	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}


	public String getTimeRule() {
		return timeRule;
	}


	public void setTimeRule(String timeRule) {
		this.timeRule = timeRule;
	}


	public Date getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getSquareUrl() {
		return squareUrl;
	}


	public void setSquareUrl(String squareUrl) {
		this.squareUrl = squareUrl;
	}


	public String getRectangleUrl() {
		return rectangleUrl;
	}


	public void setRectangleUrl(String rectangleUrl) {
		this.rectangleUrl = rectangleUrl;
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


	public List<StoreLevelVo> getStoreLevelList() {
		return storeLevelList;
	}


	public void setStoreLevelList(List<StoreLevelVo> storeLevelList) {
		this.storeLevelList = storeLevelList;
	}


	public String getCargoName() {
		return cargoName;
	}


	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}


	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo= cargo;
	}


	public String getFirstMarketPrice() {
		return firstMarketPrice;
	}


	public void setFirstMarketPrice(String firstMarketPrice) {
		this.firstMarketPrice = firstMarketPrice;
	}


	public String getFirstSalePrice() {
		return firstSalePrice;
	}


	public void setFirstSalePrice(String firstSalePrice) {
		this.firstSalePrice = firstSalePrice;
	}


	public List<TradeGoodSkuVo> getGoodSkuList() {
		return goodSkuList;
	}


	public void setGoodSkuList(List<TradeGoodSkuVo> goodSkuList) {
		this.goodSkuList = goodSkuList;
	}


	public List<GoodsBaseLabelVo> getGoodLabelsList() {
		return goodLabelsList;
	}


	public void setGoodLabelsList(List<GoodsBaseLabelVo> goodLabelsList) {
		this.goodLabelsList = goodLabelsList;
	}


	/**
	 * @return the cargoBrand
	 */
	public String getCargoBrand() {
		return cargoBrand;
	}
	

	/**
	 * @param cargoBrand the cargoBrand to set
	 */
	public void setCargoBrand(String cargoBrand) {
		this.cargoBrand = cargoBrand;
	}

	/**
	 * @return the cargoType
	 */
	public String getCargoType() {
		return cargoType;
	}

	/**
	 * @param cargoType the cargoType to set
	 */
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the tradeGoodId
	 */
	public String getTradeGoodId() {
		return tradeGoodId;
	}

	/**
	 * @param tradeGoodId the tradeGoodId to set
	 */
	public void setTradeGoodId(String tradeGoodId) {
		this.tradeGoodId = tradeGoodId;
	}

	/**
	 * @return the goodSkuId
	 */
	public String getGoodSkuId() {
		return goodSkuId;
	}

	/**
	 * @param goodSkuId the goodSkuId to set
	 */
	public void setGoodSkuId(String goodSkuId) {
		this.goodSkuId = goodSkuId;
	}

	private Long headstoreId;

    private Long cargoId;

    private String name;

    private String describe;

    private Long sort;

    private Integer type;

    private Long category;

    private String post;

    private Double postFee;

    private Long creator;

    private Date createTime;

    private Long goodId;

    private Long cargoSkuId;

    private Long nums;

//    private Double marketPrice;

    private Double retailPrice;

//    private Date startTime;
//
//    private Date endTime;

    private Integer limitNum;

//    private Double salePrice;

    private String cargoSkuName;
    
    private Integer status;

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getCargoSkuId() {
        return cargoSkuId+"";
    }

    public void setCargoSkuId(Long cargoSkuId) {
        this.cargoSkuId = cargoSkuId;
    }

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

//    public Double getMarketPrice() {
//        return marketPrice;
//    }
//
//    public void setMarketPrice(Double marketPrice) {
//        this.marketPrice = marketPrice;
//    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

//    public Double getSalePrice() {
//        return salePrice;
//    }
//
//    public void setSalePrice(Double salePrice) {
//        this.salePrice = salePrice;
//    }

    public String getCargoSkuName() {
        return cargoSkuName;
    }

    public void setCargoSkuName(String cargoSkuName) {
        this.cargoSkuName = cargoSkuName;
    }

    public Long getHeadstoreId() {
        return headstoreId;
    }

    public void setHeadstoreId(Long headstoreId) {
        this.headstoreId = headstoreId;
    }

    public String getCargoId() {
        return cargoId+"";
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
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
        this.post = post == null ? null : post.trim();
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
    
    
    
//    private Long id;

    private Long shopId;

    private String columnName;

    private Integer sellType;

    private Integer orderBy;

//  private Integer status;

    private String showyn;

    private String showpicture;

    private Long createBy;

//    private Date createTime;

    private Long updateBy;

    private Date updateTime;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Integer getSellType() {
        return sellType;
    }

    public void setSellType(Integer sellType) {
        this.sellType = sellType;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

//    public Integer getStatus() {
//        return status;
//    }
//
//    public void setStatus(Integer status) {
//        this.status = status;
//    }

    public String getShowyn() {
        return showyn;
    }

    public void setShowyn(String showyn) {
        this.showyn = showyn == null ? null : showyn.trim();
    }

    public String getShowpicture() {
        return showpicture;
    }

    public void setShowpicture(String showpicture) {
        this.showpicture = showpicture == null ? null : showpicture.trim();
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    
    private String goodColumnId;
    private String cargoBrandId;
    private String cargoClassifyId;



	public String getGoodColumnId() {
		return goodColumnId;
	}


	public void setGoodColumnId(String goodColumnId) {
		this.goodColumnId = goodColumnId;
	}


	public String getCargoBrandId() {
		return cargoBrandId;
	}


	public void setCargoBrandId(String cargoBrandId) {
		this.cargoBrandId = cargoBrandId;
	}


	public String getCargoClassifyId() {
		return cargoClassifyId;
	}


	public void setCargoClassifyId(String cargoClassifyId) {
		this.cargoClassifyId = cargoClassifyId;
	}
    
}
