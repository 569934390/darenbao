package com.club.web.stock.listener;

import com.club.web.util.listener.IBaseListener;

public interface ICargoListener extends IBaseListener {
	/**
	 * 删除货品， 
	 * @return true:成功,false:失败
	 * @throws Exception 
	 */
	public boolean deleteCargo(long cargoId);
	/**
	 * 删除货品SKU
	 * @return true:成功,false:失败
	 */
	public boolean deleteCargoSku(long skuId);
}
