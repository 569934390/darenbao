package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.web.store.vo.GoodReceiptAddrVo;

/**
 * 收货地址管理-service接口
 * 
 * @author wqh
 * @add by 2016-04-18
 */
public interface GoodReceiptAddrService {

	/**
	 * 新增收货地址信息
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	Map<String, Object> saveReceiptAddrSer(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询收货地址列表
	 * 
	 * @param userId
	 * @return List<GoodReceiptAddrVo>
	 * @add by 2016-04-18
	 */
	List<GoodReceiptAddrVo> queryReceiptAddrSer(String userId);

	/**
	 * 根据id查询收货地址信息
	 * 
	 * @param id
	 * @return GoodReceiptAddrVo
	 * @add by 2016-05-13
	 */
	GoodReceiptAddrVo getAddrByIdSer(String id);

	/**
	 * 删除收货地址列表
	 * 
	 * @param userId
	 * @param ids
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	Map<String, Object> deleteReceiptAddrSer(String userId, String ids);

	/**
	 * 修改设置默认地址
	 * 
	 * @param userId
	 * @param id
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	Map<String, Object> updateReceiptAddrStatusSer(String userId, String id) throws Exception;

	/**
	 * 修改地址信息
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	Map<String, Object> updateReceiptAddrSer(Map<String, Object> paramMap) throws Exception;

}
