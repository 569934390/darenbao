package com.club.web.works.vo;

public class FinanceAbnormal {
    private String id;
        
    private String orderId;

    private String orderName;

    private Integer status;

    private String u8orderid;

    private String u8accountsid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getU8orderid() {
		return u8orderid;
	}

	public void setU8orderid(String u8orderid) {
		this.u8orderid = u8orderid;
	}

	public String getU8accountsid() {
		return u8accountsid;
	}

	public void setU8accountsid(String u8accountsid) {
		this.u8accountsid = u8accountsid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusText() {
		return this.status != null && this.status == 0 ? "正常":"取消";
	}
}