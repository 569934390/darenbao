package com.club.web.store.vo;

import com.club.web.store.service.StoreData.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderSimpleVO {
	private long orderId;
	private OrderGoodsVO[] list;
	private OrderStatus status;
	public OrderSimpleVO(long orderId, OrderStatus status, OrderGoodsVO... list) {
		this.orderId = orderId;
		this.list = list;
		this.status = status;
	}
	public long getOrderId() {
		return orderId;
	}
	public OrderGoodsVO[] getList() {
		return list;
	}
	@JsonIgnore
	public OrderStatus getStatus() {
		return status;
	}
	public int getStatusCode(){
		return status.ordinal();
	}
	public String getStatusName(){
		return status.getChineseName();
	}
}
