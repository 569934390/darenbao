package com.club.web.coupon.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.coupon.domain.repository.CouponRepository;
@Configurable
public class CouponDo {
	@Autowired
	private CouponRepository couponRepository;
	
	 private Long id;

	    private String name;

	    private Long goodId;

	    private Date beginTime;

	    private Date endTime;

	    private Long nums;

	    private Long shopId;

	    private Long creator;

	    private Date updatetime;

	    private String twodimensionCode;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public Long getGoodId() {
	        return goodId;
	    }

	    public void setGoodId(Long goodId) {
	        this.goodId = goodId;
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

	    public Long getNums() {
	        return nums;
	    }

	    public void setNums(Long nums) {
	        this.nums = nums;
	    }

	    public Long getShopId() {
	        return shopId;
	    }

	    public void setShopId(Long shopId) {
	        this.shopId = shopId;
	    }

	    public Long getCreator() {
	        return creator;
	    }

	    public void setCreator(Long creator) {
	        this.creator = creator;
	    }

	    public Date getUpdatetime() {
	        return updatetime;
	    }

	    public void setUpdatetime(Date updatetime) {
	        this.updatetime = updatetime;
	    }

	    public String getTwodimensionCode() {
	        return twodimensionCode;
	    }

	    public void setTwodimensionCode(String twodimensionCode) {
	        this.twodimensionCode = twodimensionCode == null ? null : twodimensionCode.trim();
	    }
	    
	    public void insert(){
	    	couponRepository.addCoupon(this);
	    }
}
