package com.club.web.store.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.store.domain.RechargeNoteDo;
import com.club.web.store.domain.repository.RechargeNoteRepository;
import com.club.web.store.service.RechargeNoteService;

@Service("rechargeNoteService")
public class RechargeNoteServiceImpl implements RechargeNoteService {

	@Autowired
	private RechargeNoteRepository rechargeNoteRepository;

	@Override
	public void addRechargeNote(String body,String indentId,String paymentAmount,String payType,String buyerClient,String buyerAccount) {
		RechargeNoteDo rechargeNoteDo = new RechargeNoteDo();
		boolean addYn = rechargeNoteRepository.queryRechargeNote(Long.valueOf(indentId));
		if(addYn){
			String flag = "";
			String [] paramOut = body.split(",");
			if(paramOut != null && paramOut.length > 0){
				for(String value : paramOut){
					String [] param = value.split("=");
					if(param != null && param.length == 2){
						if("flag".equals(param[0].replace("{", "").trim())){
							flag = param[1].replace("'", "").replace("}", "").trim();
						}
					}
				}
			}
			String itemId = "0";
			if("U".equals(flag)){
				itemId = "0001";
            } else if("R".equals(flag)){
            	itemId = "0004";
            } else{
            	itemId = "0005";
            }
			
			rechargeNoteDo.setIndentId(Long.valueOf(indentId));
			rechargeNoteDo.setPaymentAmount(new BigDecimal(paymentAmount));
			rechargeNoteDo.setPayType(Integer.valueOf(payType));
			rechargeNoteDo.setItemCode(Integer.valueOf(itemId));
			rechargeNoteDo.setBuyerClient(buyerClient);
			rechargeNoteDo.setBuyerAccount(buyerAccount);
			rechargeNoteDo.setCreateDate(new Date());
			rechargeNoteDo.setState("1");	//表示还未记流水账
			rechargeNoteDo.insert();
		}
	}
	
	@Override
	public void updateRechargeNote(String indentId){
		RechargeNoteDo rechargeNoteDo = new RechargeNoteDo();
		rechargeNoteDo.setIndentId(Long.valueOf(indentId));
		rechargeNoteDo.setState("2");	//表示已记流水账	
		rechargeNoteDo.update();
	}
}