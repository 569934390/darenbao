package com.club.web.coupon.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.coupon.domain.CouponDo;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.coupon.vo.ExportCouponVo;

public interface CouponService {
	Page<Map<String, Object>> selectCoupon(Page<Map<String, Object>> page, HttpServletRequest request);

	void addCoupon(CouponVo couponVo) throws ParseException;

	void deleteCoupon(String idStr);

	void updateCoupon(CouponVo couponVo) throws ParseException;

	List<ExportCouponVo> selectCouponByIds(String idStr);

	String exportByIds(String idStr, HttpServletRequest request) throws FileNotFoundException, IOException;

	/**
	 * 查询卡劵列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponVo>
	 * @add by 2016-05-13
	 */
	List<CouponVo> getVoucherListSer(String shopId, int startIndex, int pageSize);
}
