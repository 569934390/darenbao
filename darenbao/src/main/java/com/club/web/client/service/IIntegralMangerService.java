package com.club.web.client.service;

import com.club.framework.exception.BaseAppException;

import java.util.Map;

public interface IIntegralMangerService {
	/**
	 * 更新或保存会员信息
	 * @param record
	 * @return
	 * @throws com.club.framework.exception.BaseAppException
	 */
	Object saveOrUpdateInfo(Map<String, Object> record) throws BaseAppException;

	/**
	 * 更新会员状态
	 * @param bizIdStr
	 * @param action
	 * @return
	 * @throws com.club.framework.exception.BaseAppException
	 */
	Object updateState(String bizIdStr, String action) throws BaseAppException;

	/**
	 * 获取通用规则表
	 * @return
	 */
	Object loadGenralSql();
}
