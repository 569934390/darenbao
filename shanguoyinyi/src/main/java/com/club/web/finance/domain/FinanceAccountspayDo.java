package com.club.web.finance.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.finance.domain.repository.FinanceAccountspayRepository;

@Configurable
public class FinanceAccountspayDo {
	
	@Autowired FinanceAccountspayRepository financeAccountspayRepository;
    private Long id;
    
    private String code;

    private Long orderid;
    
    private Integer statue;

    private String vouchtype;

    private Date vouchdate;

    private Long customerid;

    private String customercode;

    private String customername;

    private String departmentcode;

    private String digest;

    private String balancecode;

    private String balanceitemcode;

    private String foreigncurrency;

    private BigDecimal currencyrate;

    private BigDecimal amount;

    private BigDecimal originalamount;

    private String operator;

    private String flag;

    private String note;

    private String u8orderid;

    private String u8accountsid;
    
    private Date createTime;

    private Date updateTime;

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
		this.code = code;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Integer getStatue() {
		return statue;
	}

	public void setStatue(Integer statue) {
		this.statue = statue;
	}

	public String getVouchtype() {
        return vouchtype;
    }

    public void setVouchtype(String vouchtype) {
        this.vouchtype = vouchtype == null ? null : vouchtype.trim();
    }

    public Date getVouchdate() {
        return vouchdate;
    }

    public void setVouchdate(Date vouchdate) {
        this.vouchdate = vouchdate;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode == null ? null : customercode.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode == null ? null : departmentcode.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public String getBalancecode() {
        return balancecode;
    }

    public void setBalancecode(String balancecode) {
        this.balancecode = balancecode == null ? null : balancecode.trim();
    }

    public String getBalanceitemcode() {
        return balanceitemcode;
    }

    public void setBalanceitemcode(String balanceitemcode) {
        this.balanceitemcode = balanceitemcode == null ? null : balanceitemcode.trim();
    }

    public String getForeigncurrency() {
        return foreigncurrency;
    }

    public void setForeigncurrency(String foreigncurrency) {
        this.foreigncurrency = foreigncurrency == null ? null : foreigncurrency.trim();
    }

    public BigDecimal getCurrencyrate() {
        return currencyrate;
    }

    public void setCurrencyrate(BigDecimal currencyrate) {
        this.currencyrate = currencyrate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOriginalamount() {
        return originalamount;
    }

    public void setOriginalamount(BigDecimal originalamount) {
        this.originalamount = originalamount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void save() {
		financeAccountspayRepository.save(this);		
	}

	public void update() {
		financeAccountspayRepository.update(this);
	}
}