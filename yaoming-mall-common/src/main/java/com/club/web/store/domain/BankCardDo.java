package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import com.club.framework.util.BankCardUtil;
import com.club.framework.util.IdcardUtils;
import com.club.web.store.dao.extend.BankCardExtendMapper;
import com.club.web.store.domain.repository.BankCardRepository;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;
@Configurable
public class BankCardDo {
	   @Autowired
	   BankCardExtendMapper bankCardExtendMapper;
	   @Autowired
	   BankCardRepository bankCardRepository;
	
	   private Long bankCardId;

	    private Integer state;

	    private String name;

	    private String idCard;

	    private String bankName;

	    private String bankCard;

	    private String mobile;

	    private Date updateTime;

	    private Date createTime;

	    private Integer delet;

	    private Long connectId;

	    private Integer connectType;
	    
	    private String bankAddress;

	    public Long getBankCardId() {
	        return bankCardId;
	    }

	    public void setBankCardId(Long bankCardId) {
	    	
	        this.bankCardId = bankCardId;
	    }


	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getIdCard() {
	    	
	        return idCard;
	    }

	    public void setIdCard(String idCard) {
	    	
	    	
	        this.idCard = idCard == null ? null : idCard.trim();
	        
	        Assert.isTrue(IdcardUtils.validateCard(this.idCard), "身份证格式不正确");
	    }

	    public String getBankName() {
	        return bankName;
	    }

	    public void setBankName(String bankName) {
	        this.bankName = bankName == null ? null : bankName.trim();
	    }

	    public String getBankCard() {
	        return bankCard;
	    }

	    public void setBankCard(String bankCard) {
	    	
	    	Assert.isTrue(BankCardUtil.checkBankCard(bankCard), "银行卡格式不正确,请填真实的银行卡号");
	        this.bankCard = bankCard == null ? null : bankCard.trim();
	    }

	    public String getMobile() {
	        return mobile;
	    }

	    public void setMobile(String mobile) {
	    	Assert.isTrue(CommonUtil.isMobile(mobile), "手机号格式不正确");
				this.mobile = mobile;
	    }
	    
	    public int insert(){
	    	this.delet=0;
	    	this.state=0;
	    	Assert.isTrue(bankCardExtendMapper.selectByBanCard(bankCard)==0, "银行卡已存在");
	    	this.bankCardId = IdGenerator.getDefault().nextId();
	    	this.setCreateTime(new Date());
	    	int result=bankCardRepository.saveBankCard(this);
			return result;
	    };
        public int update(){
        	
        	this.setUpdateTime(new Date());
	    	int result=bankCardRepository.updateBankCard(this);
	    	
			return result;
	    }

        public int updateBankCardState(){
        	int result=bankCardRepository.updateBankCardState(this);
        	return result;
        }
        public int deletBySubranchID(){
        	int result=bankCardRepository.deletBankCard(this);
        	return result;
        }
		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Integer getDelet() {
			return delet;
		}

		public void setDelet(Integer delet) {
			this.delet = delet;
		}

		public Long getConnectId() {
			return connectId;
		}

		public void setConnectId(Long connectId) {
			this.connectId = connectId;
		}

		public Integer getConnectType() {
			return connectType;
		}

		public void setConnectType(Integer connectType) {
			this.connectType = connectType;
		}

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
		}

		public String getBankAddress() {
			return bankAddress;
		}

		public void setBankAddress(String bankAddress) {
			this.bankAddress = bankAddress;
		};
}
