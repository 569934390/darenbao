package com.club.web.coupon.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.coupon.domain.CouponDo;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.coupon.vo.ExportCouponVo;

public interface CouponRepository {
	List<CouponVo> queryCouponPage(Map<String, Object> map);

	Long queryCouponCountPage(Map<String, Object> map);

	void addCoupon(CouponDo couponDo);

	void deleteById(Long id);

	void update(CouponDo couponDo);

	ExportCouponVo selectById(Long id);

	/**
	 * 查询卡劵列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponVo>
	 * @add by 2016-05-13
	 */
	List<CouponVo> getVoucherList(long shopId, int startIndex, int pageSize);
}
