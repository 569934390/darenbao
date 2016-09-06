package com.club.web.client.service;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;

import java.util.Map;

public interface IAccountService {
	/**
	 * 更新或保存会员信息
	 * @return
	 * @throws com.club.framework.exception.BaseAppException
	 */
	void countClientParentIntegeral(Map<String,Object> client,Integer clientId,Double money,int deep,String orderId,Map<String,Object> ruleInfo) throws BaseAppException;

	/**
	 * 计算会员等级
	 * @param client
	 * @param action
	 * @throws BaseAppException
	 */
	void countClientParentLevel(Map<String,Object> client,String action) throws BaseAppException;
	void countClientParentLevel(Integer clientId,String action) throws BaseAppException;

	String upLevel(Integer clientId, Integer levelId) throws BaseAppException;

	/**
	 * 会员充值接口
	 * @param clientId
	 * @param total_fee
	 * @param subject
	 * @param out_trade_no
	 * @param trade_no
	 * @param trade_status
	 * @param seller_email
	 * @param buyer_email
	 * @return
	 * @throws BaseAppException
	 */
	Object recharge(Integer clientId, Float total_fee,String subject, String out_trade_no,String trade_no,
			String trade_status,String seller_email,String buyer_email,int itemId) throws BaseAppException;

	/**
	 * 会员提现接口
	 * @param bizId
	 * @param money
	 * @return
	 * @throws BaseAppException
	 */
	Map<String,Object> cash(Integer bizId,Double money)throws BaseAppException;

	/**
	 * 会员提现查询
	 * @param bizId
	 * @return
	 * @throws BaseAppException
	 */
	Page<Map<String,Object>> queryClientExchange(Integer bizId,Integer start,Integer limit) throws BaseAppException;
}
