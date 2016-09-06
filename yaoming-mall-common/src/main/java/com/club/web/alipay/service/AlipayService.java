package com.club.web.alipay.service;

/**
 * 支付宝支付服务
 * 
 * @author hqLin
 */
public interface AlipayService {
	
	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * @return
	 */
	public String getOrderNum();
	
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
	 * 根据订单号查询订单
	 * @param orderNum
	 * @return
	 */
	public int findByOrderNum(String orderNum);
	
	/**
	 * 更新订单状态
	 * @param orderData
	 */
	public void updateOrderState(String body, String moneys, String buyerId, String outTradeNo, String tradeNo, 
			String tradeStatus, String buyerEmail, String sellerId, String payType) throws Exception;
	
	String getPayInfo(int bizId, String MemberNo,String reachedLevel,String unitLevel,int renewYear,
			String flag,Long orderId) throws Exception;
}
