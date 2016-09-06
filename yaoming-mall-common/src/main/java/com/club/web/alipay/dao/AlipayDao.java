package com.club.web.alipay.dao;

public interface AlipayDao {
	
	/**
	 * 根据ID获取产品参数
	 * @param goodsId
	 * @return
	 */
	public String getGoodsData(String goodsId);
	
	/**
	 * 保存订单信息到DB
	 */
	public void AddOrderData();
	
	/**
	 * 根据订单号查新订单
	 * @param orderNum
	 * @return
	 */
	public int findByOrderNum(String orderNum);
	
	/**
	 * 更新订单状态
	 * @param orderData
	 */
	public void updateOrderState(String orderNum);
}
