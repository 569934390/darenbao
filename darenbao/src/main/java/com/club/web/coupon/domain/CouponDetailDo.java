package com.club.web.coupon.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.coupon.domain.repository.CouponDetailRepository;

@Configurable
public class CouponDetailDo {
	@Autowired
	CouponDetailRepository couponDetailRepository;
	private Long id;

	private String code;

	private String password;

	private Long couponId;

	private Integer status;

	private Long shopId;

	private String shopCode;
	
	private Date updatetime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode == null ? null : shopCode.trim();
	}

	
	
	public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
	
	public void insert() {
		couponDetailRepository.addCouponDetail(this);
	}
	
	public void update() throws Exception {
		couponDetailRepository.update(this);
	}
}
