package com.club.web.store.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.ShoppingCartRepository;

/**
 * 购物车对象
 * 
 * @author wqh
 * 
 * @add by 2016-04-07
 */
@Configurable
public class ShoppingCartDo {
	/*
	 * 注入购物车仓库
	 */
	@Autowired
	ShoppingCartRepository repository;
	private Long id;

	private Long goodsId;

	private Long shopId;

	private Long userId;

	private Integer goodsCount;

	private BigDecimal goodsPrize;

	private Date createDate;

	private Date updateDate;

	private Integer opertionType;
	/**
	 * 0-存在(更新)1-不存在(新增)
	 */
	private int flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public BigDecimal getGoodsPrize() {
		return goodsPrize;
	}

	public void setGoodsPrize(BigDecimal goodsPrize) {
		this.goodsPrize = goodsPrize;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void save() throws Exception {
		repository.save(this);
	}

	public void update(int count, int status) throws Exception {
		this.setUpdateDate(new Date());
		this.setGoodsCount(count);//山国数量
//		if (status == 0) {
//			this.setGoodsCount(this.getGoodsCount() + count);
//		}
//		if (status == 1) {
//			this.setGoodsCount(this.getGoodsCount() - count);
//		}
		repository.update(this);
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Integer getOpertionType() {
		return opertionType;
	}

	public void setOpertionType(Integer opertionType) {
		this.opertionType = opertionType;
	}
}