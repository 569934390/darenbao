package com.club.web.finance.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.finance.dao.base.po.FinanceAccountspayItem;
import com.club.web.finance.dao.extend.FinanceAccountspayExtendMapper;
import com.club.web.finance.dao.extend.FinanceAccountspayItemExtendMapper;
import com.club.web.finance.domain.FinanceAccountspayItemDo;
import com.club.web.finance.domain.repository.FinanceAccountspayItemRepository;
import com.club.web.finance.vo.FinanceAccountspayItemVo;
@Repository
public class FinanceAccountspayItemRepositoryImpl implements FinanceAccountspayItemRepository{

	@Autowired FinanceAccountspayItemExtendMapper financeAccountspayItemExtendMapper;

	@Override
	public FinanceAccountspayItemDo create(FinanceAccountspayItemVo financeAccountspayItemVo) {
		if(financeAccountspayItemVo==null){
			return null;
		}
		FinanceAccountspayItemDo financeAccountspayItemDo=new FinanceAccountspayItemDo();
		financeAccountspayItemDo.setId(Long.parseLong(financeAccountspayItemVo.getId()));
		financeAccountspayItemDo.setMainid(Long.parseLong(financeAccountspayItemVo.getMainid()));
		financeAccountspayItemDo.setType(financeAccountspayItemVo.getType());
		financeAccountspayItemDo.setCustomerid(Long.parseLong(financeAccountspayItemVo.getCustomerid()));;
		financeAccountspayItemDo.setCustomercode(financeAccountspayItemVo.getCustomercode());
		financeAccountspayItemDo.setCustomername(financeAccountspayItemVo.getCustomername());
		financeAccountspayItemDo.setDepartmentcode(financeAccountspayItemVo.getDepartmentcode());
		financeAccountspayItemDo.setAmount(financeAccountspayItemVo.getAmount());
		financeAccountspayItemDo.setOriginalamount(financeAccountspayItemVo.getOriginalamount());
		financeAccountspayItemDo.setItemcode(financeAccountspayItemVo.getItemcode());
		return financeAccountspayItemDo;
	}
	
	private FinanceAccountspayItem getPoByDo(FinanceAccountspayItemDo financeAccountspayItemDo) {
		if(financeAccountspayItemDo==null){
			return null;
		}
		FinanceAccountspayItem financeAccountspayItem=new FinanceAccountspayItem();
		BeanUtils.copyProperties(financeAccountspayItemDo, financeAccountspayItem);
		return financeAccountspayItem;
	}

	@Override
	public void save(FinanceAccountspayItemDo financeAccountspayItemDo) {
		financeAccountspayItemExtendMapper.insertSelective(getPoByDo(financeAccountspayItemDo));
	}

	@Override
	public FinanceAccountspayItemVo getFinanceAccountspayItemVoByMain(long mainid) {
		// TODO Auto-generated method stub
		return financeAccountspayItemExtendMapper.selectByMainId(mainid);
	}

}
