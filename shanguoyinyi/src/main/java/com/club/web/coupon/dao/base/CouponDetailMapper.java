package com.club.web.coupon.dao.base;

import com.club.web.coupon.dao.base.po.CouponDetail;

public interface CouponDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CouponDetail record);

    int insertSelective(CouponDetail record);

    CouponDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponDetail record);

    int updateByPrimaryKey(CouponDetail record);
}