package com.club.web.store.listener;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.listener.ICargoListener;
import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.store.dao.extend.TradeGoodSkuExtendMapper;
import com.club.web.store.domain.TradeGoodSkuDo;
import com.club.web.store.service.GoodService;

@Configurable
public class CargoListener implements ICargoListener {

	@Autowired private TradeGoodExtendMapper tradeGoodExtendDao;
	@Autowired private TradeGoodSkuExtendMapper tradeGoodSkuExtendMapper;
	@Autowired private  GoodService   goodServiceImpl;

	@Override
	public boolean deleteCargo(long cargoId){
		System.out.println("deleteCargo "+cargoId);
		Long goodId=tradeGoodExtendDao.selectGoodNumsByCargoId(cargoId);
		if(goodId!=null && goodId!=0){
			goodServiceImpl.deleteGoods(goodId+"");
//			return true;
//		}else{
//			return true;
		}
		return true;
	}

	@Override
	public boolean deleteCargoSku(long skuId) {
		System.out.println("deleteCargoSku "+skuId);
		List<TradeGoodSkuDo> list=tradeGoodSkuExtendMapper.selectGoodSkuNumsByCargoSkuId(skuId);
		if(list==null || list.size()==0){
			return true;
		}else{
			throw new RuntimeException("该货品Sku已经被某商品Sku引用，不可删除");
		}
	}

}
