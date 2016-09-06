package com.club.web.client.service;

import java.util.List;
import java.util.Map;

import com.club.core.common.TreeNode;
import com.club.framework.exception.BaseAppException;

public interface IClientService {
	/**
	 * 更新或保存会员信息
	 * @param clientMap
	 * @return
	 * @throws BaseAppException
	 */
	Map<String,Object> saveOrUpdateClientInfo(Map<String, Object> clientMap) throws BaseAppException;

	/**
	 * 更新或保存店铺管理员
	 * @param clientMap
	 * @return
	 * @throws BaseAppException
	 */
	
	Map<String,Object> saveOrUpdateSubbranchClientInfo(Map<String, Object> clientMap) throws BaseAppException;
	
	/**
	 * 更新会员状态
	 * @param bizIdStr
	 * @param action
	 * @return
	 * @throws BaseAppException
	 */
	Object updateClientState(String bizIdStr,String action,String context) throws BaseAppException;

	/**
	 * 更新或保存会员信息
	 * @param clientMap
	 * @return
	 * @throws BaseAppException
	 */
	Object saveOrUpdateLevelInfo(Map<String, Object> clientMap) throws BaseAppException;

	/**
	 * 更新会员状态
	 * @param bizIdStr
	 * @param action
	 * @return
	 * @throws BaseAppException
	 */
	Object updateLevelState(String bizIdStr,String action) throws BaseAppException;

	/**
	 * 获取会员6层子节点
	 * @param client
	 * @param deep
	 * @return
	 */
	TreeNode loadSubClient(Map<String,Object> client,int deep,List<Map<String,Object>> subs);

	/**
	 * 获取详细用户信息
	 * @param bizId
	 * @return
	 * @throws BaseAppException
	 */
	default Map<String,Object> loadClientInfo(Integer bizId) throws BaseAppException {
		return loadClientInfo(Long.valueOf(bizId));
	}
	Map<String,Object> loadClientInfo(Long bizId) throws BaseAppException;

	/**
	 * 会员提现接口
	 * @param bizId
	 * @param money
	 * @return
	 * @throws BaseAppException
	 */
	Map<String,Object> cash(Integer bizId,Double money)throws BaseAppException;
}
