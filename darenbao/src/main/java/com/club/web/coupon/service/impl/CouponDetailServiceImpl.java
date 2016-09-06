package com.club.web.coupon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.coupon.dao.extend.CouponDetailExtendMapper;
import com.club.web.coupon.domain.CouponDetailDo;
import com.club.web.coupon.domain.repository.CouponDetailRepository;
import com.club.web.coupon.service.CouponDetailService;
import com.club.web.coupon.vo.CouponDetailVo;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.util.CommonUtil;
import com.yaoming.common.util.IdGenerator;

@Service("couponDetailService")
public class CouponDetailServiceImpl implements CouponDetailService {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(CouponDetailServiceImpl.class);
	@Autowired
	CouponDetailRepository couponDetailRepository;
	@Autowired
	SubbranchService subbranchService;
	@Autowired
	CouponDetailExtendMapper couponDetailDao;

	@Override
	public Page<Map<String, Object>> selectCouponDetail(Page<Map<String, Object>> page, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		map.put("couponId", page.getConditons().get("couponId").toString());
		map.put("conditions", page.getConditons().get("conditions").toString());
		map.put("statusConditions", page.getConditons().get("statusConditions").toString());
		List<CouponDetailVo> list = couponDetailRepository.queryCouponDetailPage(map);
		result.setResultList(CommonUtil.listObjTransToListMap(list));
		Long count = couponDetailRepository.queryCouponDetailCountPage(map);
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 删除多张兑换券详情(支持批量删除)
	 */
	@Override
	public void deleteCouponDetail(String idStr) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			couponDetailRepository.deleteById(Long.parseLong(id));
		}
	}

	/**
	 * 禁用兑换券编码
	 * 
	 * @throws Exception
	 */
	@Override
	public void forbidStatus(String idStr) throws Exception {
		String[] ids = idStr.split(",");
		for (String id : ids) {
			CouponDetailDo obj = couponDetailRepository.getVoucherDetail(Long.valueOf(id));
			obj.setStatus(3);
			obj.setUpdatetime(new Date());
			obj.update();
		}
	}

	/**
	 * 
	 * @param id
	 *            兑换券编码id
	 * @param shopId
	 *            店铺id
	 * @param shopCode
	 *            店铺编号
	 */
	@Override
	public void allocateShop(String idStr, String shopId, String shopCode) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			CouponDetailDo obj = couponDetailRepository.getVoucherDetail(Long.valueOf(id));
			obj.setShopId(Long.valueOf(shopId));
			obj.setShopCode(shopCode);
			obj.setStatus(1);
			obj.setUpdatetime(new Date());
			obj.update();
		}
	};

	/**
	 * 查询卡劵明细列表
	 * 
	 * @param vocherId
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	@Override
	public List<CouponDetailVo> getVoucherDetailListSer(String vocherId, int status, int startIndex, int pageSize) {
		List<CouponDetailVo> list = null;
		long id = 0;
		long nowDate = new Date().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(vocherId)) {
			id = Long.valueOf(vocherId);
			list = couponDetailRepository.getVoucherDetailList(id, status, startIndex, pageSize);
			if (list != null && list.stream().count() > 0) {
				list.stream().forEach(arg0 -> {
					if (1 == status) {
						if (nowDate > arg0.getEndTime().getTime()) {
							arg0.setStatus(4);
							arg0.setStatusName("已过期");
						}
					}
					if (1 == arg0.getStatus()) {
						arg0.setStatusName("未兑换");
					}
					if (3 == arg0.getStatus()) {
						arg0.setStatusName("已禁用");
					}
					if (2 == arg0.getStatus()) {
						arg0.setStatusName("已兑换");
					}
					if (arg0.getBeginTime() != null) {
						arg0.setBeginDate(format.format(arg0.getBeginTime()));
					}
					if (arg0.getEndTime() != null) {
						arg0.setEndDate(format.format(arg0.getEndTime()));
					}
				});
			}
		}
		return list;
	}

	/**
	 * 搜索卡劵明细列表
	 * 
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	public List<CouponDetailVo> getVoucherDetailListSer(String code, int startIndex, int pageSize) {
		List<CouponDetailVo> list = couponDetailRepository.getVoucherDetailList(code, startIndex, pageSize);
		long nowDate = new Date().getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (list != null && list.stream().count() > 0) {
			list.stream().forEach(arg0 -> {
				if (arg0.getStatus() == 1) {
					if (nowDate > arg0.getEndTime().getTime()) {
						arg0.setStatus(4);
						arg0.setStatusName("已过期");
					}
				}
				if (1 == arg0.getStatus()) {
					arg0.setStatusName("未兑换");
				}
				if (3 == arg0.getStatus()) {
					arg0.setStatusName("已禁用");
				}
				if (2 == arg0.getStatus()) {
					arg0.setStatusName("已兑换");
				}
				if (arg0.getBeginTime() != null) {
					arg0.setBeginDate(format.format(arg0.getBeginTime()));
				}
				if (arg0.getEndTime() != null) {
					arg0.setEndDate(format.format(arg0.getEndTime()));
				}
			});
		}
		return list;
	}

	/**
	 * 设置卡劵禁用
	 * 
	 * @param vouchId
	 * @param status
	 * @return Map<String, String>
	 * @add by 2016-05-16
	 */
	@Override
	public Map<String, Object> setVoucherStatusSer(long vouchId, int status) throws Exception {
		Map<String, Object> result = new HashMap<>();
		CouponDetailDo obj = couponDetailRepository.getVoucherDetail(vouchId);
		if (obj != null) {
			obj.setStatus(status);
			obj.setUpdatetime(new Date());
			obj.update();
			result.put(Constants.RESULT_MSG, "操作成功");
			result.put(Constants.RESULT_CODE, "0");
		} else {
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, "根据id查询对象不存在");
		}
		return result;
	}

	@Override
	public Map<String, Object> addCouponDetail(List<CouponDetailVo> couponDetailVoList, String couponId) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		// 校验店铺编号是否存在
		for (CouponDetailVo couponDetailVo : couponDetailVoList) {
			if (couponDetailVo.getShopCode() != null && !couponDetailVo.getShopCode().equals("")) {
				SubbranchVo subbranchVo = null;
				subbranchVo = subbranchService.selectByNumber(couponDetailVo.getShopCode());
				if (subbranchVo == null) {
					result.put("ifExgistShopCode", 0);
					result.put("info", "店铺编号为" + couponDetailVo.getShopCode() + "的店铺不存在，请修改后再导入");
					return result;
				}
			}
		}
		for (CouponDetailVo couponDetailVo : couponDetailVoList) {
			CouponDetailDo couponDetailDo = new CouponDetailDo();
			couponDetailDo.setId(IdGenerator.getDefault().nextId());
			couponDetailDo.setCouponId(Long.parseLong(couponId));
			couponDetailDo.setCode(couponDetailVo.getCode());
			couponDetailDo.setpassword(couponDetailVo.getpassword());
			couponDetailDo.setUpdatetime(new Date());
			if (couponDetailVo.getShopCode() != null && !couponDetailVo.getShopCode().equals("")) {
				couponDetailDo.setStatus(1);
				couponDetailDo.setShopCode(couponDetailVo.getShopCode());
				SubbranchVo subbranchVo = null;
				subbranchVo = subbranchService.selectByNumber(couponDetailVo.getShopCode());
				if (subbranchVo != null) {
					couponDetailDo.setShopId(Long.parseLong(subbranchVo.getId()));

				}
			} else {
				couponDetailDo.setStatus(0);
			}
			couponDetailDo.insert();
		}
		result.put("ifExgistShopCode", 1);
		return result;
	}

	@Override
	public List<CouponDetailVo> queryBycouponId(String couponId) {
		// TODO Auto-generated method stub
		List<CouponDetailVo> list = null;
		list = couponDetailRepository.queryBycouponId(Long.parseLong(couponId));
		return list;
	}

	/**
	 * 验证兑换券编码和密码是否正确，并且验证是否已经过期
	 */
	public Map<String, Object> validateCoupon(String code, String password,String goodId) {
		Map<String, Object> map = new HashMap<String, Object>();
		long goodIdLong = 0;
		if(goodId != null && !goodId.isEmpty()){
			goodIdLong = Long.valueOf(goodId);
		}
		CouponDetailVo couponDetail = couponDetailRepository.validateCoupon(code, password, goodIdLong);
		Date date = new Date();
		// 如果查出的对象为空，则说明编号或者密码错误
		if (couponDetail == null) {
			map.put("success", false);
			map.put("msg", "兑换券编码或者密码错误");
			return map;
		} else {
			// 判断是否在有效期之内
			if (couponDetail.getBeginTime().getTime() > date.getTime()
					|| couponDetail.getEndTime().getTime() < date.getTime()) {
				map.put("success", false);
				map.put("msg", "很抱歉，此兑换券已过期");
				return map;
			} else if(couponDetail.getStatus() == 0){
				map.put("success", false);
				map.put("msg", "兑换券未分配,不能兑换");
				return map;
			} else if(couponDetail.getStatus() == 2){
				map.put("success", false);
				map.put("msg", "兑换券已兑换,不能再次兑换");
				return map;
			} else if(couponDetail.getStatus() == 3){
				map.put("success", false);
				map.put("msg", "兑换券已禁用,不能兑换");
				return map;
			} else {
				// 兑换成功
				map.put("success", true);
				map.put("msg", "兑换券兑换成功");
				map.put("shopId", couponDetail.getShopId());
				return map;
			}
		}
	}

	/**
	 * 订单下单后修改兑换券编码状态-改为已兑换
	 */
	@Override
	public void modifyStatus(String code) throws Exception {
		// TODO Auto-generated method stub
		CouponDetailDo obj = couponDetailDao.selectBycode(code);
		if (obj != null) {
			obj.setUpdatetime(new Date());
			obj.setStatus(2);
			obj.update();
		}
	}

}
