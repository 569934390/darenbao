package com.club.web.deal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.club.core.common.Page;
import com.club.web.deal.exception.IndentException;
import com.club.web.deal.vo.IndentExtendVo;
import com.club.web.deal.vo.IndentMobileVo;
import com.club.web.deal.vo.IndentU8Vo;
import com.club.web.deal.vo.IndentVo;

/**
 * 订单服务接口
 * 
 * @author zhuzd
 *
 */
public interface IndentService {

	IndentVo findVoById(Long id) throws Exception;
	
	void shipNotice(Long id) throws IndentException;

	Map<String, Object> list(Page<Map<String, Object>> page) throws Exception;

	List<IndentVo> findVoListByIds(String ids) throws Exception;

	/**
	 * 更新订单状态
	 * 
	 * @param ids
	 *            ,订单id列表，多个id用,隔开
	 * @param loginMap
	 *            ,人员相关数据
	 * @param action
	 *            ,后续流程动作 参考IndentStatus的name属性值
	 * @param map
	 *            ,需要用到的参数值，例如文本
	 */
	void updateStatus(String ids, String action, Map<String, Object> loginMap, Map<String, Object> map)
			throws Exception;

	void add(IndentVo indentVo) throws Exception;

	List<IndentMobileVo> findVoMobileListByBuyerId(Long buyerId, Long storeId, String status, int startIndex, int pageSize)
			throws Exception;

	/**
	 * 查询店铺的订单列表
	 * 
	 * @param shopId
	 * @param userId 
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return List<IndentMobileVo>
	 * @add by 2016-05-13
	 * 
	 */
	List<IndentExtendVo> getShopOrderListSer(Long shopId, Long userId, String status, int startIndex, int pageSize) throws Exception;

	/**
	 * 查询店铺订单统计值
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-17
	 */
	Map<String, Object> getOrderTotalMsgSer(Long shopId);

	Page<Map<String, Object>> cargoChecklist(Page<Map<String, Object>> page);

	int cargoCheckCount();

	void delete(String ids) throws Exception;

	IndentU8Vo findU8VoById(long orderId);

	IndentVo findVoForWeixinCallBackById(Long id);

	void exportExcel(String condition, HttpServletResponse response)throws Exception;

	void updateList(Map<String, Object> con) throws IndentException, Exception ;

}
