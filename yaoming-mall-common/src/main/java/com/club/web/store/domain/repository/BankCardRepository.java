package com.club.web.store.domain.repository;

import com.club.web.store.domain.BankCardDo;

public interface BankCardRepository {

	int saveBankCard(BankCardDo bankCardDo);
	int updateBankCard(BankCardDo bankCardDo);
	int updateBankCardState(BankCardDo bankCardDo);
	int deletBankCard(BankCardDo bankCardDo);
}
