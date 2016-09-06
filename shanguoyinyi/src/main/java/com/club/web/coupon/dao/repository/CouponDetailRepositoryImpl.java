package com.club.web.coupon.dao.repository;

import java.util.List;
import java.util.Map;

import com.club.framework.util.BeanUtils;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.coupon.constant.CouponStatusEnum;
import com.club.web.coupon.dao.base.po.CouponDetail;
import com.club.web.coupon.dao.extend.CouponDetailExtendMapper;
import com.club.web.coupon.domain.CouponDetailDo;
import com.club.web.coupon.domain.repository.CouponDetailRepository;
import com.club.web.coupon.vo.CouponDetailVo;

@Repository
public class CouponDetailRepositoryImpl implements CouponDetailRepository {

	@Autowired
	CouponDetailExtendMapper couponDetailDao;

	@Override
	public List<CouponDetailVo> queryCouponDetailPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CouponDetailVo> list = couponDetailDao.queryCouponDetailPage(map);
		for (CouponDetailVo couponDetailVo : list) {
			couponDetailVo.setStatusName(CouponStatusEnum.getTextByDbData(couponDetailVo.getStatus()));
		}
		return list;
	}

	@Override
	public Long queryCouponDetailCountPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return couponDetailDao.queryCouponDetailCountPage(map);
	}

	@Override
	public void deleteById(Long id) {
		couponDetailDao.deleteByPrimaryKey(id);
	}

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
	@Override
	public List<CouponDetailVo> getVoucherDetailList(long id, int status, int startIndex, int pageSize) {
		List<CouponDetailVo> list = couponDetailDao.getVoucherDetailList(id, status, startIndex, pageSize);
		return list;
	}

	/**
	 * 搜索卡劵明细列表
	 * 
	 * @param code
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	@Override
	public List<CouponDetailVo> getVoucherDetailList(String code, int startIndex, int pageSize) {
		List<CouponDetailVo> list = couponDetailDao.serachVoucherDetailList(code, startIndex, pageSize);
		return list;
	}

	/**
	 * 查询卡劵明细对象
	 * 
	 * @param id
	 * @return CouponDetailDo
	 * @add by 2016-05-16
	 */
	@Override
	public CouponDetailDo getVoucherDetail(long id) {
		CouponDetailDo obj = couponDetailDao.getVoucherDetail(id);
		return obj;
	}

	/**
	 * 修改卡劵明细对象
	 * 
	 * @param CouponDetailDo
	 * @return void
	 * @add by 2016-05-16
	 */
	@Override
	public void update(CouponDetailDo obj) {
		CouponDetail po = new CouponDetail();
		BeanUtils.copyProperties(obj, po);
		couponDetailDao.updateByPrimaryKey(po);
	}
	@Override
	public void addCouponDetail(CouponDetailDo couponDetailDo){
		CouponDetail po = new CouponDetail();
		BeanUtils.copyProperties(couponDetailDo, po);
		couponDetailDao.insert(po);
	}
	
	@Override
	public List<CouponDetailVo> queryBycouponId(Long couponId){
		return couponDetailDao.queryBycouponId(couponId);
	}
	
	public CouponDetailVo validateCoupon(String code,String password,long goodId){
		return couponDetailDao.validateCoupon(code, password, goodId);
	}

}
