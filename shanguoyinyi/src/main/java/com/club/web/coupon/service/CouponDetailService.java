package com.club.web.coupon.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.coupon.vo.CouponDetailVo;

public interface CouponDetailService {
	Page<Map<String, Object>> selectCouponDetail(Page<Map<String, Object>> page, HttpServletRequest request);

	void deleteCouponDetail(String idStr);

	void forbidStatus(String idStr) throws Exception;

	void allocateShop(String idStr, String shopId, String shopCode) throws Exception;

	/**
	 * 查询卡劵明细列表
	 * 
	 * @param vocherId
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	List<CouponDetailVo> getVoucherDetailListSer(String vocherId, int status, int startIndex, int pageSize);

	/**
	 * 搜索卡劵明细列表
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	List<CouponDetailVo> getVoucherDetailListSer(String code, int startIndex, int pageSize);

	/**
	 * 设置卡劵禁用
	 * 
	 * @param vouchId
	 * @param status
	 * @return Map<String, String>
	 * @add by 2016-05-16
	 */
	Map<String, Object> setVoucherStatusSer(long vouchId, int status) throws Exception;

	Map<String, Object> addCouponDetail(List<CouponDetailVo> couponDetailVoList, String couponId);
	
	List<CouponDetailVo> queryBycouponId(String couponId);
	/**
	 * 卡券校验编号密码
	 * @param code
	 * @param password
	 * @return
	 */
	Map<String,Object> validateCoupon(String code,String password,String goodId);
	void modifyStatus(String code) throws Exception;
}
