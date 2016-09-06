package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.GoodReceiptAddrDo;
import com.club.web.store.vo.GoodReceiptAddrVo;

/**
 * 收货地址仓库
 * 
 * @author wqh
 * 
 * @add by 2016-04-18
 */
public interface ReceiptAddrRepository {
	/**
	 * 创建对象
	 * 
	 * @param paramMap
	 * @return GoodReceiptAddrDo
	 * @add by 2016-04-18
	 */
	GoodReceiptAddrDo createReceiptAddtObj(Map<String, Object> paramMap);

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-18
	 */
	<T> void save(T t) throws Exception;

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-18
	 */
	<T> void update(T t) throws Exception;

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-18
	 */
	void updateNoExists(long id);

	/**
	 * 根据用户Id查询地址列表
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-18
	 */
	List<GoodReceiptAddrVo> queryReceiptAddrList(long userId);

	/**
	 * 根据id查询收货地址信息
	 * 
	 * @param id
	 * @return GoodReceiptAddrVo
	 * @add by 2016-05-13
	 */
	GoodReceiptAddrVo getAddrById(long id);

	/**
	 * 删除收货地址列表
	 * 
	 * @param userId
	 * @param ids
	 * @return void
	 * @add by 2016-04-18
	 */
	void deleteReceiptAddr(long userId, List<Long> ids);

	/**
	 * 根据参数查询对象
	 * 
	 * @param userId
	 * @param id
	 * @return GoodReceiptAddrDo
	 * @add by 2016-04-18
	 */
	GoodReceiptAddrDo queryByCondition(long userId, long id);
}
