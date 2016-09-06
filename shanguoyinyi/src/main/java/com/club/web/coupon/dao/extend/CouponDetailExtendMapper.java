package com.club.web.coupon.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.coupon.dao.base.CouponDetailMapper;
import com.club.web.coupon.domain.CouponDetailDo;
import com.club.web.coupon.vo.CouponDetailVo;

public interface CouponDetailExtendMapper extends CouponDetailMapper {
	Long queryCouponDetailCountPage(Map<String, Object> map);

	List<CouponDetailVo> queryCouponDetailPage(Map<String, Object> map);

	void forbidStatus(@Param("id") Long id);

	void allocateShop(@Param("id") Long id, @Param("shopId") Long shopId, @Param("shopCode") String shopCode);

	List<CouponDetailVo> getVoucherDetailList(@Param("id") long id, @Param("status") int status,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	List<CouponDetailVo> serachVoucherDetailList(@Param("code") String code, @Param("startIndex") int startIndex,
			@Param("pageSize") int pageSize);

	CouponDetailDo getVoucherDetail(@Param("id") long id);
	List<CouponDetailVo> queryBycouponId(@Param("couponId") Long couponId);
	
	Long queryAllCouponDetailCount(@Param("couponId") Long couponId);
	
	CouponDetailVo validateCoupon(@Param("code") String code,@Param("password") String password,@Param("goodId") long goodId);
	CouponDetailDo selectBycode(@Param("code") String code);
}
