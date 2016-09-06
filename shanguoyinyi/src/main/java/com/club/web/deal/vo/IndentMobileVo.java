package com.club.web.deal.vo;

import java.util.Date;
import java.util.List;

import com.club.web.deal.constant.IndentDealStatus;
import com.club.web.deal.constant.IndentStatus;

/**
 * 订单手机端Vo
 * @author zhuzd
 *
 */
public class IndentMobileVo {
	
    private String id;
    
    private String totalAmount;

    private String paymentAmount;

    private Integer number;

    private Integer status;
    
    private Integer dealStatus;
    
    private String refundName;

    private String returnName;

    private String refundRemark;

    private String returnRemark;

    private String rejectRefund;

    private String rejectReturn;
    
    private Date createTime;
    
    private Integer type;
    
	private String buyerCarriage;
    
    private List<IndentListVo> indentList; 
    
    public String getStatusText(){
    	return this.status != null?IndentStatus.getTextByDbData(this.status):null;
    }
    
    public String getDealStatusText(){
    	return this.dealStatus != null?IndentDealStatus.getTextByDbData(this.dealStatus):null;
    }
    
	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getRefundName() {
		return refundName;
	}

	public void setRefundName(String refundName) {
		this.refundName = refundName;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}
	
	public String getRefundRemark() {
		return refundRemark;
	}

	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}

	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public String getRejectRefund() {
		return rejectRefund;
	}

	public void setRejectRefund(String rejectRefund) {
		this.rejectRefund = rejectRefund;
	}

	public String getRejectReturn() {
		return rejectReturn;
	}

	public void setRejectReturn(String rejectReturn) {
		this.rejectReturn = rejectReturn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<IndentListVo> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<IndentListVo> indentList) {
		this.indentList = indentList;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBuyerCarriage() {
		return buyerCarriage;
	}

	public void setBuyerCarriage(String buyerCarriage) {
		this.buyerCarriage = buyerCarriage;
	}
}