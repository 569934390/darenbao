package com.club.web.store.domain.repository;

import com.club.web.store.domain.RechargeNoteDo;

public interface RechargeNoteRepository {
	
	boolean queryRechargeNote(Long indentId);
	int addRechargeNote(RechargeNoteDo rechargeNoteDo);
	int updateRechargeNote(RechargeNoteDo rechargeNoteDo);
}