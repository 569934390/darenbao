package com.club.web.store.vo;

import java.util.Date;

public class TradeGoodSkuVo {
	
	private int leftNums;
	
	private String skuLong;
	
    public int getLeftNums() {
		return leftNums;
	}

	public void setLeftNums(int leftNums) {
		this.leftNums = leftNums;
	}

	private String id;

    private String goodId;

    private String cargoSkuId;

    private Long nums=0l;

    private Double marketPrice;

    private Double retailPrice;

    private Date startTime;

    private Date endTime;

    private Integer limitNum=0;

    private Double salePrice;

    private String cargoSkuName;
    
    private Integer saleNum=0;

    private String goodName;
    
    private String code;
    
    

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getCargoSkuId() {
        return cargoSkuId;
    }

    public void setCargoSkuId(String cargoSkuId) {
        this.cargoSkuId = cargoSkuId;
    }

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getCargoSkuName() {
        return cargoSkuName;
    }

    public void setCargoSkuName(String cargoSkuName) {
        this.cargoSkuName = cargoSkuName;
    }

	public String getSkuLong() {
		return skuLong;
	}

	public void setSkuLong(String skuLong) {
		this.skuLong = skuLong;
	}
}