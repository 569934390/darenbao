package com.club.web.coupon.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.coupon.dao.base.CouponMapper;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.coupon.vo.ExportCouponVo;

public interface CouponExtendMapper extends CouponMapper {
	Long queryCouponCountPage(Map<String, Object> map);

	List<CouponVo> queryCouponPage(Map<String, Object> map);

	List<CouponVo> getVoucherList(@Param("shopId") long shopId, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	ExportCouponVo selectExportCoupon(@Param("id") Long id);
}
