package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.GoodRepository;
import com.club.web.store.domain.repository.GoodSKURepository;

@Configurable
public class TradeGoodSkuDo {
	
	@Autowired
	GoodSKURepository goodSkuRepository;
	
	 private Long id;

	    private Long goodId;

	    private Long cargoSkuId;

	    private String cargoSkuName;

	    private Long nums;

	    private Double marketPrice;

	    private Double retailPrice;

	    private Date startTime;

	    private Date endTime;

	    private Integer limitNum;

	    private Double salePrice;
	    
	    private Integer saleNum;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getGoodId() {
	        return goodId;
	    }

	    public void setGoodId(Long goodId) {
	        this.goodId = goodId;
	    }

	    public Long getCargoSkuId() {
	        return cargoSkuId;
	    }

	    public void setCargoSkuId(Long cargoSkuId) {
	        this.cargoSkuId = cargoSkuId;
	    }

	    public String getCargoSkuName() {
	        return cargoSkuName;
	    }

	    public void setCargoSkuName(String cargoSkuName) {
	        this.cargoSkuName = cargoSkuName == null ? null : cargoSkuName.trim();
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
	    
	    
    
    public Integer getSaleNum() {
			return saleNum;
		}

		public void setSaleNum(Integer saleNum) {
			this.saleNum = saleNum;
		}

	/**
     * 保存自身对象
     * 
     * @Description:
     */
    public void saveSelf(){
    	goodSkuRepository.addGoodSKU(this);
    }
}