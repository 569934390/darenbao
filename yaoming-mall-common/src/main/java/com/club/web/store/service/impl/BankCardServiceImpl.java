package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.web.store.dao.base.po.BankCard;
import com.club.web.store.dao.extend.BankCardExtendMapper;
import com.club.web.store.domain.BankCardDo;
import com.club.web.store.domain.emu.BankCardEMU;
import com.club.web.store.service.BankCardService;
import com.club.web.store.vo.BankCardExtendVo;
import com.club.web.store.vo.BankCardVo;
import com.club.web.util.CommonUtil;

@Service("bankCardService")
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	BankCardExtendMapper bankCardExtendMapper;
	Map<String, Object> result = null;

	@Override
	public int saveOrUpdateBankCard(BankCardVo bankCardVo) {
        
		BankCardDo bankCardDo = new BankCardDo();
		BeanUtils.copyProperties(bankCardVo,bankCardDo);
		int result;
		if (bankCardDo.getBankCardId() != null) {
			result = bankCardDo.update();
		} else {

			result = bankCardDo.insert();
		}

		return result;
	}

	@Override
	public Page getBankCardList(Page page) {
		String name = "";
		String connectId = "";
		if (page.getConditons() != null) {
			if (page.getConditons().get("name") != null) {
				name = page.getConditons().get("name").toString();
			}
			if (page.getConditons().get("connectId") != null) {
				connectId = page.getConditons().get("connectId").toString();
			}
		}

		int total = bankCardExtendMapper.selectByPageCount(name, connectId);
		List<BankCardVo> bankCardVoList = new ArrayList<>();
		if (total > page.getLimit()) {
			bankCardVoList = bankCardExtendMapper.selectByPage(name, connectId, page.getStart(), page.getLimit());
		} else {
			bankCardVoList = bankCardExtendMapper.selectByPage(name, connectId, page.getStart(), total);
		}

		page.setTotalRecords(total);

		page.setResultList(bankCardVoList);
		return page;
	}

	@Override
	public int updateBankCardState(String[] bankCardIds, String action) {
		BankCardDo bankCardDo = new BankCardDo();

		try {
			if (action != null) {
				for (String bankCardId : bankCardIds) {
					if (bankCardId != null && bankCardId != "") {
						bankCardDo.setState(Integer.parseInt(action));
						bankCardDo.setBankCardId(Long.parseLong(bankCardId));
						bankCardDo.updateBankCardState();
					}
				}
			}

		} catch (Exception e) {

		}

		return 0;
	}

	@Override
	public BankCardEMU[] getBankCardTypeList() {

		return BankCardEMU.values();
	}

	/**
	 * 查看银行卡列表
	 * 
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<BankCardExtendVo>
	 * @add by 2016-05-12
	 */
	@Override
	public List<BankCardExtendVo> getBankCardListSer(String shopId, int pageSize, int pageNum) {
		List<BankCardExtendVo> list = null;
		long shop = 0;
		if (pageSize < 1) {
			pageSize = 6;
		}
		if (pageNum < 0) {
			pageNum = 1;
		}
		if (StringUtils.isNotEmpty(shopId)) {
			shop = Long.valueOf(shopId);
			list = bankCardExtendMapper.getBankCardList(shop, (pageNum - 1) * pageSize, pageSize);
			/*if (list != null && list.stream().count() > 0) {
				list.stream().forEach(args -> {
					if (StringUtils.isNotEmpty(args.getCard())) {
						args.setCardShow(CommonUtil.getHideIdCardVal(args.getCard(), 5, 4, 4));
					}
				});
			}*/
		}
		return list;
	}

	/**
	 * 查看银行卡列表根据id
	 * 
	 * @param id
	 * @return BankCardExtendVo
	 * @add by 2016-05-12
	 */
	@Override
	public BankCardExtendVo getBankCardSer(String id) {
		BankCardExtendVo obj = null;
		long bankId = 0l;
		if (StringUtils.isNotEmpty(id)) {
			bankId = Long.valueOf(id);
			obj=bankCardExtendMapper.getBankCardObj(bankId);
		}
		return obj;
	}
}
