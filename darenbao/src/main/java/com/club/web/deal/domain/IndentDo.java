package com.club.web.deal.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.deal.domain.repository.IndentListRepository;
import com.club.web.deal.domain.repository.IndentRepository;
/**
 * 订单Do
 * @author zhuzd
 *
 */
@Configurable
public class IndentDo {
    private Long id;

    private String name;

    private Long tradeHeadStoreId;

    private Long subbranchId;

    private Long buyerId;

    private Long referrerId;

    private BigDecimal totalAmount;

    private BigDecimal paymentAmount;

    private Date createTime;

    private Date payTime;

    private Integer number;

    private Integer type;

    private String province;

    private String city;

    private String town;

    private String address;

    private String receiverPhone;

    private String buyerRemark;

    private String expressNumber;

    private String expressCompany;

    private String weight;

    private BigDecimal carriage;
    
    private BigDecimal buyerCarriage;

    private String shipper;

    private Date shipTime;

    private String receiver;

    private Integer status;

    private Integer payType;

    private String payAccount;

    private Boolean needInvoice;

    private String invoiceName;

    private String invoiceContent;

    private String remark;

    private Date finishTime;
    
    private Boolean deleteFlag;

    private Integer dealStatus;

    private Long refundId;

    private Long returnId;

    private String refundRemark;

    private String returnRemark;

    private String rejectRefund;

    private String rejectReturn;
    
    private String ticketNum;
    
    private String buyType;
    
    private Integer shipNotice;
    
    @Autowired
    private IndentRepository indentRepository;
    
	@Autowired
	private IndentListRepository indentListRepository; 
	
	public List<IndentListDo> getIndentList() {
		return indentListRepository.getDoListByIndentId(this.getId());
	}
    
	public void save() {
		indentRepository.add(this);
	}
	
	public void update() {
		indentRepository.modify(this);		
	}
	
	public void delete() {
		for (IndentListDo indentListDo : getIndentList()) {
			indentListDo.delete();
		}
		indentRepository.delete(this.getId());		
	}
	
	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
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

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTradeHeadStoreId() {
        return tradeHeadStoreId;
    }

    public void setTradeHeadStoreId(Long tradeHeadStoreId) {
        this.tradeHeadStoreId = tradeHeadStoreId;
    }

    public Long getSubbranchId() {
        return subbranchId;
    }

    public void setSubbranchId(Long subbranchId) {
        this.subbranchId = subbranchId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(Long referrerId) {
        this.referrerId = referrerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
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

    public BigDecimal getCarriage() {
        return carriage;
    }

    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage == null ? null : carriage;
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

    public Boolean getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Boolean needInvoice) {
        this.needInvoice = needInvoice;
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

	public BigDecimal getBuyerCarriage() {
		return buyerCarriage;
	}

	public void setBuyerCarriage(BigDecimal buyerCarriage) {
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
}