package com.club.web.coupon.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.coupon.dao.base.po.Coupon;
import com.club.web.coupon.dao.extend.CouponDetailExtendMapper;
import com.club.web.coupon.dao.extend.CouponExtendMapper;
import com.club.web.coupon.domain.CouponDo;
import com.club.web.coupon.domain.repository.CouponRepository;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.coupon.vo.ExportCouponVo;

@Repository
public class CouponRepositoryImpl implements CouponRepository {
	@Autowired
	CouponExtendMapper couponDao;
	
	@Autowired
	CouponDetailExtendMapper couponDetailDao;

	@Override
	public List<CouponVo> queryCouponPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CouponVo> list=couponDao.queryCouponPage(map);
		for (CouponVo couponVo : list) {
			couponVo.setNums(couponDetailDao.queryAllCouponDetailCount(Long.parseLong(couponVo.getId())));
		}
		return list;
	}

	@Override
	public Long queryCouponCountPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return couponDao.queryCouponCountPage(map);
	}

	@Override
	public void addCoupon(CouponDo couponDo) {
		// TODO Auto-generated method stub
		Coupon coupon = new Coupon();
		BeanUtils.copyProperties(couponDo, coupon);
		couponDao.insert(coupon);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		couponDao.deleteByPrimaryKey(id);
	}

	@Override
	public void update(CouponDo couponDo) {
		// TODO Auto-generated method stub
		Coupon coupon = new Coupon();
		BeanUtils.copyProperties(couponDo, coupon);
		couponDao.updateByPrimaryKey(coupon);
	}

	@Override
	public ExportCouponVo selectById(Long id) {
		// TODO Auto-generated method stub
		return couponDao.selectExportCoupon(id);
	}

	/**
	 * 查询卡劵列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponVo>
	 * @add by 2016-05-13
	 */
	@Override
	public List<CouponVo> getVoucherList(long shopId, int startIndex, int pageSize) {
		List<CouponVo> list = couponDao.getVoucherList(shopId, startIndex, pageSize);
		return list;
	}
}
