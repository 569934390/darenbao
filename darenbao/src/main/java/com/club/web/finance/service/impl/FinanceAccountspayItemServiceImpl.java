package com.club.web.finance.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.web.finance.domain.FinanceAccountspayItemDo;
import com.club.web.finance.domain.repository.FinanceAccountspayItemRepository;
import com.club.web.finance.service.FinanceAccountspayItemService;
import com.club.web.finance.vo.FinanceAccountspayItemVo;
import com.club.web.util.IdGenerator;

@Service("financeAccountspayItemService")
public class FinanceAccountspayItemServiceImpl implements FinanceAccountspayItemService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	FinanceAccountspayItemRepository financeAccountspayItemRepository;

	/**
	 * 保存更新收款单明细
	 */
	@Override
	public Map<String, Object> saveOrUpdate(FinanceAccountspayItemVo financeAccountspayItemVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (financeAccountspayItemVo == null) {
			result.put("success", false);
			result.put("msg", "收款单明细不能为空！");
			return result;
		}
		if (financeAccountspayItemVo.getId() == null || "".equals(financeAccountspayItemVo.getId())) {
			financeAccountspayItemVo.setId(IdGenerator.getDefault().nextId()+"");
			FinanceAccountspayItemDo financeAccountspayItemDo = financeAccountspayItemRepository
					.create(financeAccountspayItemVo);
			financeAccountspayItemDo.save();
		}
		result.put("success", true);
		return result;
	}

	@Override
	public FinanceAccountspayItemVo getFinanceAccountspayItemVo(long mainid) {
		// TODO Auto-generated method stub
		return financeAccountspayItemRepository.getFinanceAccountspayItemVoByMain(mainid);
	}

}
