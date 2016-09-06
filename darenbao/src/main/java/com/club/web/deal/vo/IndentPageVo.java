package com.club.web.deal.vo;

import java.util.Date;

import com.club.framework.util.StringUtils;
import com.club.web.deal.constant.IndentStatus;
import com.club.web.deal.constant.IndentType;

/**
 * 订单Vo
 * @author zhuzd
 *
 */
public class IndentPageVo {

    private String id;

    private String name;

    private String tradeHeadStoreId;
    
    private String tradeHeadStoreName;
    
    private String subbranchId;
    
    private String subbranchName;
        
    private String buyerId;
    
    private String buyerName;
    
    private String totalAmount;

    private String paymentAmount;

    private Date createTime;

    private Integer number;

    private Integer type;

    private String province;

    private String city;

    private String town;

    private String address;

    private String receiverPhone;

    private String carriage;
    
    private String buyerCarriage;

    private String receiver;

    private Integer status;

    private Boolean needInvoice = false;

    private String invoiceName;

    private String invoiceContent;
    
    private Integer shipNotice;

	private String buyType;
    
	public String getFullAddress(){
    	return (StringUtils.isNotEmpty(this.province)?this.province:"")
    			+(StringUtils.isNotEmpty(this.city)?this.city:"")
    			+(StringUtils.isNotEmpty(this.town)?this.town:"")
    			+(StringUtils.isNotEmpty(this.address)?this.address:"");
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

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage == null ? null : carriage.trim();
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

    public String getNeedInvoiceText(){
    	return this.needInvoice?"是":"否";
    }
    
    public Boolean getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Boolean needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceText() {
    	if(this.needInvoice != null && this.needInvoice){
    		if(!"个人".equals(this.invoiceName)){
                return this.invoiceContent;
        	}
            return this.invoiceName;
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
    
    public String getStoreName() {
		return StringUtils.isNotEmpty(this.tradeHeadStoreName)?this.tradeHeadStoreName:(StringUtils.isNotEmpty(this.subbranchName)?this.subbranchName:"");
	}
    
    public String getTypeName(){
    	if(this.type != null){
    		return IndentType.getTextByDbData(this.type);
    	}
    	return "";
    }
    
    public String getStatusName(){
    	if(this.status != null){
    		return IndentStatus.getTextByDbData(this.status);
    	}
    	return "";
    }

	public String getBuyerCarriage() {
		return buyerCarriage;
	}

	public void setBuyerCarriage(String buyerCarriage) {
		this.buyerCarriage = buyerCarriage;
	}

	public String getTradeHeadStoreName() {
		return tradeHeadStoreName;
	}

	public void setTradeHeadStoreName(String tradeHeadStoreName) {
		this.tradeHeadStoreName = tradeHeadStoreName;
	}

	public String getSubbranchName() {
		return subbranchName;
	}

	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Integer getShipNotice() {
		return shipNotice;
	}

	public void setShipNotice(Integer shipNotice) {
		this.shipNotice = shipNotice;
	}
	
	public String getBuyType() {
		return buyType;
	}

	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	
	public String getBuyTypeText() {
		return "s".equalsIgnoreCase(this.buyType)?"送人":"自用";
	}
}