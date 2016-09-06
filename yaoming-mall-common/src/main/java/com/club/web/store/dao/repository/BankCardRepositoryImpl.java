package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.BankCardMapper;
import com.club.web.store.dao.base.po.BankCard;
import com.club.web.store.dao.extend.BankCardExtendMapper;
import com.club.web.store.domain.BankCardDo;
import com.club.web.store.domain.repository.BankCardRepository;


@Repository
public class BankCardRepositoryImpl implements BankCardRepository {

	@Autowired
	BankCardExtendMapper bankCardExtendMapper;
	
	@Override
	public int saveBankCard(BankCardDo bankCardDo) {
		int result=0;
		BankCard bankCard=new BankCard();
		BeanUtils.copyProperties(bankCardDo, bankCard);
		
		result=bankCardExtendMapper.insert(bankCard);
		
		return result;
	}
    
	/*public BankCard exchange(BankCardDo bankCardDo){
		
		BankCard bankCard=new BankCard();
	    bankCard.setBankCard(bankCardDo.getBankCard());
	    bankCard.setBankCardId(bankCardDo.getBankCardId());
	    bankCard.setBankName(bankCardDo.getBankName());
	    bankCard.setIdCard(bankCardDo.getIdCard());
	    bankCard.setMobile(bankCardDo.getMobile());
	    bankCard.setName(bankCardDo.getName());
	    bankCard.setUpdateTime(bankCardDo.getUpdateTime());
	    bankCard.setCreateTime(bankCardDo.getCreateTime());
	    bankCard.setConnectId(bankCardDo.getConnectId());
		bankCard.setConnectType(bankCardDo.getConnectType());
		bankCard.setState(bankCardDo.getState());
		bankCard.setDelet(bankCardDo.getDelet());
		return bankCard;
	}*/

	@Override
	public int updateBankCard(BankCardDo bankCardDo) {
        BankCard bankCard=new BankCard();
		BeanUtils.copyProperties(bankCardDo, bankCard);
		
		int result=bankCardExtendMapper.updateByPrimaryKeySelective(bankCard);
		
		return result;
	}
	@Override
	public int updateBankCardState(BankCardDo bankCardDo) {
		BankCard bankCard=new BankCard();
		BeanUtils.copyProperties(bankCardDo, bankCard);
		 int result=bankCardExtendMapper.updateByPrimaryKeySelective(bankCard);
		return result;
	}

	@Override
	public int deletBankCard(BankCardDo bankCardDo) {
		int result=bankCardExtendMapper.deleteBySubbranchID(bankCardDo.getConnectId());
		return result;
	}
	
}
