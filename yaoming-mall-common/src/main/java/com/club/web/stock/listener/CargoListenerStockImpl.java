package com.club.web.stock.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.domain.repository.StockManagerRepository;

@Configurable
public class CargoListenerStockImpl implements ICargoListener {
	/**
	 * 注入库存仓库
	 */
	@Autowired
	StockManagerRepository repository;

	@Override
	public boolean deleteCargo(long cargoId) {
		boolean flag = false;
		int count = repository.queryStockTotalByCargoId(cargoId);
		if (count > 0) {
			flag = false;
		} else {
			repository.deleteStockByCargoId(cargoId);
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean deleteCargoSku(long skuId) {
		boolean flag = false;
		int count = repository.queryStockTotalBySkuId(skuId);
		if (count > 0) {
			flag = false;
		} else {
			repository.deleteStockBySkuId(skuId);
			flag = true;
		}
		return flag;
	}

}
