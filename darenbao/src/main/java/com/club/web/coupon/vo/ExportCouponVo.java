package com.club.web.coupon.vo;

import java.util.Date;

public class ExportCouponVo {
	private String name;
	
	//商品名称
    private String goodName;
    
    private Date beginTime;

    private Date endTime;
    
    private Long nums;
    
    private byte[] erweima;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
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

	public byte[] getErweima() {
		return erweima;
	}

	public void setErweima(byte[] erweima) {
		this.erweima = erweima;
	}
    
}
