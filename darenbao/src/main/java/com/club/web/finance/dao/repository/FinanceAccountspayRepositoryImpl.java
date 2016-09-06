package com.club.web.finance.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.finance.dao.base.po.FinanceAccountspay;
import com.club.web.finance.dao.extend.FinanceAccountspayExtendMapper;
import com.club.web.finance.domain.FinanceAccountspayDo;
import com.club.web.finance.domain.repository.FinanceAccountspayRepository;
import com.club.web.finance.vo.FinanceAccountspayVo;

@Repository
public class FinanceAccountspayRepositoryImpl implements FinanceAccountspayRepository {

	@Autowired
	FinanceAccountspayExtendMapper financeAccountspayExtendMapper;

	@Override
	public FinanceAccountspayDo create(FinanceAccountspayVo financeAccountspayVo) {
		if (financeAccountspayVo == null) {
			return null;
		}
		FinanceAccountspayDo financeAccountspayDo = new FinanceAccountspayDo();
		BeanUtils.copyProperties(financeAccountspayVo, financeAccountspayDo);
		financeAccountspayDo.setId(Long.parseLong(financeAccountspayVo.getId()));
		financeAccountspayDo.setOrderid(Long.parseLong(financeAccountspayVo.getOrderid()));
		financeAccountspayDo.setCustomerid(Long.parseLong(financeAccountspayVo.getCustomerid()));
		return financeAccountspayDo;
	}

	private FinanceAccountspay getPoByDo(FinanceAccountspayDo financeAccountspayDo) {
		if (financeAccountspayDo == null) {
			return null;
		}
		FinanceAccountspay financeAccountspay = new FinanceAccountspay();
		BeanUtils.copyProperties(financeAccountspayDo, financeAccountspay);
		return financeAccountspay;
	}

	private FinanceAccountspayDo getDoByPo(FinanceAccountspay financeAccountspay) {
		if (financeAccountspay == null) {
			return null;
		}
		FinanceAccountspayDo financeAccountspayDo = new FinanceAccountspayDo();
		BeanUtils.copyProperties(financeAccountspay, financeAccountspayDo);
		return financeAccountspayDo;
	}
	

	private FinanceAccountspay getPoByVo(FinanceAccountspayVo financeAccountspayVo) {
		if (financeAccountspayVo == null) {
			return null;
		}
		FinanceAccountspay financeAccountspay = new FinanceAccountspay();
		BeanUtils.copyProperties(financeAccountspayVo, financeAccountspay);
		financeAccountspay.setId(Long.parseLong(financeAccountspayVo.getId()));
		financeAccountspay.setOrderid(Long.parseLong(financeAccountspayVo.getOrderid()));
		financeAccountspay.setCustomerid(Long.parseLong(financeAccountspayVo.getCustomerid()));
		return financeAccountspay;
	}

	@Override
	public void save(FinanceAccountspayDo financeAccountspayDo) {
		financeAccountspayExtendMapper.insertSelective(getPoByDo(financeAccountspayDo));
	}

	@Override
	public FinanceAccountspayDo load(long id) {
		return getDoByPo(financeAccountspayExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public void update(FinanceAccountspayDo financeAccountspayDo) {
		financeAccountspayExtendMapper.updateByPrimaryKeySelective(getPoByDo(financeAccountspayDo));
	}

	@Override
	public FinanceAccountspayVo getVoByOrderId(String orderId) {
		return financeAccountspayExtendMapper.getVoByOrderId(orderId);
	}

	@Override
	public List<FinanceAccountspayVo> selectByU8OrderNull() {
		// TODO Auto-generated method stub
		return financeAccountspayExtendMapper.selectByU8OrderNull();
	}
	public List<FinanceAccountspayVo> selectByU8AccountsNull() {
		// TODO Auto-generated method stub
		return financeAccountspayExtendMapper.selectByU8AccountsNull();
	}

}
