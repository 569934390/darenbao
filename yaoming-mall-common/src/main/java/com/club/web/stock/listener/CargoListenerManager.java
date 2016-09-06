package com.club.web.stock.listener;

import org.springframework.stereotype.Component;

import com.club.web.util.listener.BaseListenerMgr;

@Component
public class CargoListenerManager extends BaseListenerMgr<ICargoListener> implements ICargoListener {

	public CargoListenerManager() {
		super(ICargoListener.class);
	}

	@Override
	public boolean deleteCargo(long cargoId) {
		boolean flag = true;
		for (ICargoListener clazz : getClasses())
			if(!(flag = clazz.deleteCargo(cargoId)))
				break;
		return flag;
	}

	@Override
	public boolean deleteCargoSku(long skuId) {
		boolean flag = true;
		for (ICargoListener clazz : getClasses())
			if(!(flag = clazz.deleteCargoSku(skuId)))
				break;
		return flag;
	}

}
