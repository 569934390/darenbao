package com.club.web.store.dao.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.StoreSupplyPriceMapper;
import com.club.web.store.dao.base.po.StoreSupplyPrice;
import com.club.web.store.dao.extend.StoreSupplyPriceExtendMapper;
import com.club.web.store.domain.StoreSupplyPriceDo;
import com.club.web.store.vo.StoreLevelVo;
import com.club.web.store.vo.StoreSupplyPriceVo;
@Repository
public class StoreSupplyPriceRepositoryImpl implements com.club.web.store.domain.repository.StoreSupplyPriceRepository {
	@Autowired
	StoreSupplyPriceExtendMapper mapper;
	@Override
	public void saveSupplyPrice(StoreSupplyPriceDo supplyPriceDo) {
		// TODO Auto-generated method stub
		StoreSupplyPrice supplyPrice=new StoreSupplyPrice();
		BeanUtils.copyProperties(supplyPriceDo,supplyPrice);
		mapper.insert(supplyPrice);
	}
	
	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		mapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void deleteBySkuId(long id) {
		// TODO Auto-generated method stub
		mapper.deleteBySkuId(id);
	}
	
	public void deleteByGoodId(long goodId){
		mapper.deleteByGoodId(goodId);
	}
	
	public void updateSupplyPrice(StoreSupplyPriceDo supplyPriceDo){
		StoreSupplyPrice supplyPrice=new StoreSupplyPrice();
		BeanUtils.copyProperties(supplyPriceDo,supplyPrice);
		mapper.updateByPrimaryKey(supplyPrice);
	}
	
	public List<StoreSupplyPriceVo> findByGoodId(long goodId){
		return mapper.findByGoodId(goodId);
		
	};

}
