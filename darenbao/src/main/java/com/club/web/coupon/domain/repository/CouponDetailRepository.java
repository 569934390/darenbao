package com.club.web.coupon.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.coupon.domain.CouponDetailDo;
import com.club.web.coupon.domain.CouponDo;
import com.club.web.coupon.vo.CouponDetailVo;

public interface CouponDetailRepository {

	List<CouponDetailVo> queryCouponDetailPage(Map<String, Object> map);

	Long queryCouponDetailCountPage(Map<String, Object> map);

	void deleteById(Long id);

	/**
	 * 查询卡劵明细列表
	 * 
	 * @param id
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	List<CouponDetailVo> getVoucherDetailList(long id, int status, int startIndex, int pageSize);

	/**
	 * 搜索卡劵明细列表
	 * 
	 * @param code
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	List<CouponDetailVo> getVoucherDetailList(String code, int startIndex, int pageSize);

	/**
	 * 查询卡劵明细对象
	 * 
	 * @param id
	 * @return CouponDetailDo
	 * @add by 2016-05-16
	 */
	CouponDetailDo getVoucherDetail(long id);

	/**
	 * 修改卡劵明细对象
	 * 
	 * @param CouponDetailDo
	 * @return void
	 * @add by 2016-05-16
	 */
	void update(CouponDetailDo obj);
	void addCouponDetail(CouponDetailDo couponDetailDo);

	List<CouponDetailVo> queryBycouponId(Long couponId);
	
	public CouponDetailVo validateCoupon(String code,String password,long goodId);
}
