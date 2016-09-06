package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.SalesReturnReason;
import com.club.web.store.dao.extend.SalesReturnReasonExtendMapper;
import com.club.web.store.domain.SalesReturnReasonDo;
import com.club.web.store.domain.repository.SalesReturnReasonRepository;
@Repository
public class SalesReturnReasonRepositoryImpl implements SalesReturnReasonRepository{
@Autowired
SalesReturnReasonExtendMapper salesReturnReasonExtendMapper;
	@Override
	public int insert(SalesReturnReasonDo salesReturnReasonDo) {
		SalesReturnReason salesReturnReason=new SalesReturnReason();
		BeanUtils.copyProperties(salesReturnReasonDo, salesReturnReason);
		int result=salesReturnReasonExtendMapper.insert(salesReturnReason);
		return result;
	}

	@Override
	public int update(SalesReturnReasonDo salesReturnReasonDo) {
		SalesReturnReason salesReturnReason=new SalesReturnReason();
		BeanUtils.copyProperties(salesReturnReasonDo, salesReturnReason);
		int result=salesReturnReasonExtendMapper.updateByPrimaryKeySelective(salesReturnReason);
		return result;
	}

	@Override
	public int delet(SalesReturnReasonDo salesReturnReasonDo) {
		int result=salesReturnReasonExtendMapper.deleteByPrimaryKey(salesReturnReasonDo.getId());
		return 0;
	}

}
