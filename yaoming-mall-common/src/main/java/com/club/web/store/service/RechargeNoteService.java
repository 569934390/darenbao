package com.club.web.store.service;

import org.springframework.stereotype.Service;

@Service
public interface RechargeNoteService {
	
	void addRechargeNote(String body,String indentId,String paymentAmount,String payType,String buyerClient,String buyerAccount);
	
	void updateRechargeNote(String indentId);
}
