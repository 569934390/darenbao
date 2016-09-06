package com.club.web.deal.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.club.framework.util.StringUtils;
import com.club.web.deal.constant.IndentDealStatus;
import com.club.web.deal.constant.IndentPayType;
import com.club.web.deal.constant.IndentStatus;
import com.club.web.deal.constant.IndentType;

/**
 * 订单Vo
 * 
 * @author zhuzd
 *
 */
public class IndentVo {
	private String id;

	private String name;

	private String tradeHeadStoreId;

	private String subbranchId;

	private String storeName;

	private String buyerId;

	private Map<String, Object> buyer;

	private String referrerId;

	private Map<String, Object> referrer;

	private String totalAmount;

	private String paymentAmount;

	private Date createTime;

	private Date payTime;

	private Integer number;

	private Integer type;

	private String province;

	private String city;

	private String town;

	private String townCode;

	private String address;

	private String receiverPhone;

	private String buyerRemark;

	private String expressNumber;

	private String expressCompany;

	private String weight;

	private String carriage;

	private String buyerCarriage;

	private String shipper;

	private Date shipTime;

	private String receiver;

	private Integer status;

	private Integer payType;

	private String payAccount;

	private Boolean needInvoice = false;

	private String invoiceName;

	private String invoiceContent;

	private String remark;

	private Date finishTime;

	private Boolean deleteFlag = false;

	private Integer dealStatus;

	private String refundId;

	private String returnId;

	private String refundName;

	private String returnName;

	private String refundRemark;

	private String returnRemark;

	private String rejectRefund;

	private String rejectReturn;

	private String ticketNum;
	
	private String buyType;
	
    private Integer shipNotice;

	private List<IndentListVo> indentList;

	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
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

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getDealStatusText() {
		return this.dealStatus != null ? IndentDealStatus.getTextByDbData(this.dealStatus) : null;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
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

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Map<String, Object> getBuyer() {
		return buyer;
	}

	public void setBuyer(Map<String, Object> buyer) {
		this.buyer = buyer;
	}

	public Map<String, Object> getReferrer() {
		return referrer;
	}

	public void setReferrer(Map<String, Object> referrer) {
		this.referrer = referrer;
	}

	public String getFullAddress() {
		return (StringUtils.isNotEmpty(this.province) ? this.province : "")
				+ (StringUtils.isNotEmpty(this.city) ? this.city : "")
				+ (StringUtils.isNotEmpty(this.town) ? this.town : "")
				+ (StringUtils.isNotEmpty(this.address) ? this.address : "");
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<IndentListVo> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<IndentListVo> indentList) {
		this.indentList = indentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTradeHeadStoreId() {
		return tradeHeadStoreId;
	}

	public void setTradeHeadStoreId(String tradeHeadStoreId) {
		this.tradeHeadStoreId = tradeHeadStoreId;
	}

	public String getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(String subbranchId) {
		this.subbranchId = subbranchId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(String referrerId) {
		this.referrerId = referrerId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town == null ? null : town.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
	}

	public String getBuyerRemark() {
		return buyerRemark;
	}

	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark == null ? null : buyerRemark.trim();
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber == null ? null : expressNumber.trim();
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany == null ? null : expressCompany.trim();
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight == null ? null : weight.trim();
	}

	public String getCarriage() {
		return carriage;
	}

	public void setCarriage(String carriage) {
		this.carriage = carriage == null ? null : carriage.trim();
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper == null ? null : shipper.trim();
	}

	public Date getShipTime() {
		return shipTime;
	}

	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver == null ? null : receiver.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount == null ? null : payAccount.trim();
	}

	public String getNeedInvoiceText() {
		return this.needInvoice ? "是" : "否";
	}

	public Boolean getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Boolean needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getInvoiceText() {
		if (this.needInvoice != null && this.needInvoice) {
			if (!"个人".equals(this.invoiceName)) {
				return this.invoiceContent;
			}
//			return this.invoiceName;
		}
		return "";
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName == null ? null : invoiceName.trim();
	}

	public String getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getStoreName() {
		return storeName;
	}

	public String getBuyerName() {
		// 买家昵称
		return this.buyer != null && StringUtils.isNotEmpty(this.buyer.get("nickname") + "")
				? this.buyer.get("nickname") + "" : "";
	}

	public String getTypeName() {
		if (this.type != null) {
			return IndentType.getTextByDbData(this.type);
		}
		return "";
	}

	public String getStatusName() {
		if (this.status != null) {
			return IndentStatus.getTextByDbData(this.status);
		}
		return "";
	}

	public String getPayTypeName() {
		if (this.payType != null) {
			return IndentPayType.getTextByDbData(this.payType);
		}
		return "";
	}

	public String getBuyerCarriage() {
		return buyerCarriage;
	}

	public void setBuyerCarriage(String buyerCarriage) {
		this.buyerCarriage = buyerCarriage;
	}
	
	public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType == null ? null : buyType.trim();
    }

	public Integer getShipNotice() {
		return shipNotice;
	}

	public void setShipNotice(Integer shipNotice) {
		this.shipNotice = shipNotice;
	}

	public String getTownCode() {
		return townCode;
	}

	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}
}