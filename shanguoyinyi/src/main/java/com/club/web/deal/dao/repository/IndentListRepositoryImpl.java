package com.club.web.deal.dao.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.deal.dao.base.po.IndentList;
import com.club.web.deal.dao.extend.IndentListExtendMapper;
import com.club.web.deal.domain.IndentListDo;
import com.club.web.deal.domain.repository.IndentListRepository;

/**
 * 订单条目仓库接口实现类
 * @author zhuzd
 *
 */
@Repository
public class IndentListRepositoryImpl implements IndentListRepository {

	@Autowired
	private IndentListExtendMapper indentListDao;

	@Override
	public List<IndentListDo> getDoListByIndentId(Long indentId) {
		return getDomainListByPoList(indentListDao.findByIndentId(indentId));
	}

	@Override
	public int add(IndentListDo indentListDo) {
		return indentListDao.insert(getPoByDomain(indentListDo));
	}

	@Override
	public int modify(IndentListDo indentListDo) {
		return indentListDao.updateByPrimaryKeySelective(getPoByDomain(indentListDo));
	}

	@Override
	public String findImgUrlByGoodSkuId(Long goodSkuId) {
		return indentListDao.findImgUrlByGoodSkuId(goodSkuId);
	}

	@Override
	public String findSkuLongByGoodSkuId(Long goodSkuId) {
		return indentListDao.findSkuLongByGoodSkuId(goodSkuId);
	}

	@Override
	public void delete(Long id) {
		indentListDao.deleteByPrimaryKey(id);
	}
	
	private List<IndentListDo> getDomainListByPoList(List<IndentList> srcs){
		List<IndentListDo> targets = new ArrayList<IndentListDo>();
		if(srcs != null && srcs.size() != 0){
			for (IndentList src : srcs) {
				targets.add(getDomainByPo(src));
			}
		}
		return targets;
	}
	
	private IndentList getPoByDomain(IndentListDo src){
		IndentList target = null;
		if(src != null){
			target = new IndentList();
			target.setId(src.getId());
			target.setIndentId(src.getIndentId());
			target.setNumber(src.getNumber());
			target.setFinalAmount(src.getFinalAmount());
			target.setTradeGoodAmount(src.getTradeGoodAmount());
			target.setTradeGoodSkuId(src.getTradeGoodSkuId());
			target.setCargoSkuId(src.getCargoSkuId());
			target.setTradeGoodImgUrl(src.getTradeGoodImgUrl());
			target.setTradeGoodType(src.getTradeGoodType());			
			target.setTradeGoodName(src.getTradeGoodName());			
			target.setSupplyPrice(src.getSupplyPrice());
			target.setCargoNo(src.getCargoNo());	
		}
		return target;
	}

	private IndentListDo getDomainByPo(IndentList src){
		IndentListDo target = null;
		if(src != null){
			target = new IndentListDo();
			target.setId(src.getId());
			target.setIndentId(src.getIndentId());
			target.setNumber(src.getNumber());
			target.setFinalAmount(src.getFinalAmount());
			target.setTradeGoodAmount(src.getTradeGoodAmount());
			target.setTradeGoodSkuId(src.getTradeGoodSkuId());
			target.setCargoSkuId(src.getCargoSkuId());
			target.setTradeGoodImgUrl(src.getTradeGoodImgUrl());
			target.setTradeGoodType(src.getTradeGoodType());			
			target.setTradeGoodName(src.getTradeGoodName());	
			target.setSupplyPrice(src.getSupplyPrice());
			target.setCargoNo(src.getCargoNo());	
		}
		return target;
	}

}
