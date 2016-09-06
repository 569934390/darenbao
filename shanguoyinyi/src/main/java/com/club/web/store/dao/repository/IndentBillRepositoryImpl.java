package com.club.web.store.dao.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.IndentBill;
import com.club.web.store.dao.extend.IndentBillExtendMapper;
import com.club.web.store.domain.IndentBillDo;
import com.club.web.store.domain.repository.IndentBillRepository;
import com.club.web.store.vo.IndentBillVo;

@Repository
public class IndentBillRepositoryImpl implements IndentBillRepository {

	@Autowired
	IndentBillExtendMapper billExtendMapper;

	@Override
	public int insert(IndentBillDo indentBillDo) {
		IndentBill indentBill = new IndentBill();
		BeanUtils.copyProperties(indentBillDo, indentBill);

		int result = billExtendMapper.insert(indentBill);

		return result;
	}
	
	@Override
	public int update(IndentBillDo indentBillDo) {
		IndentBill indentBill = new IndentBill();
		BeanUtils.copyProperties(indentBillDo, indentBill);

		int result = billExtendMapper.updateStatusByIndentId(indentBill);

		return result;
	}
	
	@Override
	public IndentBillDo voChangeDo(IndentBillVo indentBillVo) {
		IndentBillDo newIndentBillDo = create();
		if(indentBillVo != null){
			BeanUtils.copyProperties(indentBillVo, newIndentBillDo);
			if(indentBillVo.getId() != null && !indentBillVo.getId().isEmpty()){
				newIndentBillDo.setId(Long.valueOf(indentBillVo.getId()));
			}
			if(indentBillVo.getIndentId() != null && !indentBillVo.getIndentId().isEmpty()){
				newIndentBillDo.setIndentId(Long.valueOf(indentBillVo.getIndentId()));
			}
		}
		
		return newIndentBillDo;
	}
	
	@Override
	public IndentBillDo create(){
		IndentBillDo indentBillDo = new IndentBillDo();
		
		return indentBillDo;
	}

	@Override
	public Map<String, Object> getShopIdAndSkuId(long shopId, long skuId) {
		return billExtendMapper.getShopIdAndSkuId(shopId,skuId);
	}
}