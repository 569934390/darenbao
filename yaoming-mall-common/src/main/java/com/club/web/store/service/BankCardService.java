package com.club.web.store.service;

import java.util.List;

import com.club.core.common.Page;
import com.club.web.store.domain.emu.BankCardEMU;
import com.club.web.store.vo.BankCardExtendVo;
import com.club.web.store.vo.BankCardVo;

public interface BankCardService {

	int saveOrUpdateBankCard(BankCardVo bankCardVo);

	Page getBankCardList(Page page);

	int updateBankCardState(String[] bankCardIds, String action);

	BankCardEMU[] getBankCardTypeList();

	/**
	 * 查看银行卡列表
	 * 
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<BankCardExtendVo>
	 * @add by 2016-05-12
	 */
	List<BankCardExtendVo> getBankCardListSer(String shopId, int pageSize, int pageNum);

	/**
	 * 查看银行卡列表根据id
	 * 
	 * @param id
	 * @return BankCardExtendVo
	 * @add by 2016-05-12
	 */
	BankCardExtendVo getBankCardSer(String id);

}
