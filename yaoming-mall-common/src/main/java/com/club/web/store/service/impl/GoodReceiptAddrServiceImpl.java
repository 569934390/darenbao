package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.domain.GoodReceiptAddrDo;
import com.club.web.store.domain.repository.ReceiptAddrRepository;
import com.club.web.store.service.GoodReceiptAddrService;
import com.club.web.store.vo.GoodReceiptAddrVo;
import com.club.web.util.CommonUtil;

/**
 * 收货地址管理-service接口
 * 
 * @author wqh
 * @add by 2016-04-18
 */
@Service
@Transactional
public class GoodReceiptAddrServiceImpl implements GoodReceiptAddrService {
	private Logger logger = LoggerFactory.getLogger(GoodReceiptAddrServiceImpl.class);
	private Map<String, Object> result;
	@Autowired
	ReceiptAddrRepository repository;

	/**
	 * 新增收货地址信息
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@Override
	public Map<String, Object> saveReceiptAddrSer(Map<String, Object> paramMap) throws Exception {
		// 参数校验
		result = checkReceiptAddrParam(paramMap);
		GoodReceiptAddrDo receipt = null;
		if (result != null && Constants.RESULT_SUCCESS_CODE.equals(result.get(Constants.RESULT_CODE).toString())) {
			receipt = repository.createReceiptAddtObj(paramMap);
			if (receipt != null) {
				receipt.save();
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "保存成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		}
		return result;
	}

	/**
	 * 新增收货地址信息参数校验
	 * 
	 * @param paramMap
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	private Map<String, Object> checkReceiptAddrParam(Map<String, Object> paramMap) {
		result = new HashMap<String, Object>();
		try {
			String mobile = StringUtils.EMPTY;
			String fiedxPhone = StringUtils.EMPTY;
			String receiptEmail = StringUtils.EMPTY;
			String detailAddr = StringUtils.EMPTY;
			String mailbox = StringUtils.EMPTY;
			if (paramMap != null) {
				if (paramMap.containsKey("userId")) {
					Long.valueOf(paramMap.get("userId").toString());
				}
				if (paramMap.containsKey("mobile")) {
					mobile = paramMap.get("mobile") != null ? paramMap.get("mobile").toString() : "";
				}
				if (paramMap.containsKey("fiexdPhone")) {
					fiedxPhone = paramMap.get("fiexdPhone") != null ? paramMap.get("fiexdPhone").toString() : "";
				}
				if (paramMap.containsKey("receiptEmail")) {
					receiptEmail = paramMap.get("receiptEmail") != null ? paramMap.get("receiptEmail").toString() : "";
				}
				if (paramMap.containsKey("detailAddr")) {
					detailAddr = paramMap.get("detailAddr") != null ? paramMap.get("detailAddr").toString() : "";
				}
				if (paramMap.containsKey("mailbox")) {
					mailbox = paramMap.get("mailbox") != null ? paramMap.get("mailbox").toString() : "";
				}
				if (!CommonUtil.isMobile(mobile)) {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "手机号码格式有误");
					return result;
				}
				if (StringUtils.isNotEmpty(fiedxPhone)) {
					if (!CommonUtil.isPhone(fiedxPhone)) {
						result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
						result.put(Constants.RESULT_MSG, "固话号码格式有误");
						return result;
					}
				}
				if (StringUtils.isNotEmpty(receiptEmail)) {
					if (!CommonUtil.checkEmail(receiptEmail)) {
						result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
						result.put(Constants.RESULT_MSG, "邮箱格式有误");
						return result;
					}
				}
				if (StringUtils.isEmpty(detailAddr)) {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "收货地址不能为空");
					return result;
				}
				if (StringUtils.isNotEmpty(mailbox)) {
					if (!CommonUtil.isDigital(mailbox)) {
						result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
						result.put(Constants.RESULT_MSG, "邮编格式错误");
						return result;
					}
				}
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("新增收货地址信息参数校验异常<checkReceiptAddrParam>:", e);
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数有误");
		}
		return result;
	}

	/**
	 * 查询收货地址列表
	 * 
	 * @param userId
	 * @return List<GoodReceiptAddrVo>
	 * @add by 2016-04-18
	 */
	@Override
	public List<GoodReceiptAddrVo> queryReceiptAddrSer(String userId) {
		List<GoodReceiptAddrVo> list = null;
		long user = 0;
		if (StringUtils.isNotEmpty(userId)) {
			user = Long.valueOf(userId);
			list = repository.queryReceiptAddrList(user);
		}
		return list;
	}

	/**
	 * 根据id查询收货地址信息
	 * 
	 * @param id
	 * @return GoodReceiptAddrVo
	 * @add by 2016-05-13
	 */
	@Override
	public GoodReceiptAddrVo getAddrByIdSer(String id) {
		GoodReceiptAddrVo obj = null;
		long addId = 0;
		if (StringUtils.isNotEmpty(id)) {
			addId = Long.valueOf(id);
			obj = repository.getAddrById(addId);
		}
		return obj;
	}

	/**
	 * 删除收货地址列表
	 * 
	 * @param userId
	 * @param ids
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@Override
	public Map<String, Object> deleteReceiptAddrSer(String userId, String ids) {
		result = new HashMap<>();
		long user = 0;
		String[] idsArr = null;
		List<Long> list = null;
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(ids)) {
			user = Long.valueOf(userId);
			idsArr = ids.split(",");
			if (idsArr != null && idsArr.length > 0) {
				list = new ArrayList<>();
				for (String str : idsArr) {
					list.add(Long.valueOf(str));
				}
			}
		}
		if (list != null && list.stream().count() > 0) {
			repository.deleteReceiptAddr(user, list);
			result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
			result.put(Constants.RESULT_MSG, "删除成功");
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 修改设置默认地址
	 * 
	 * @param userId
	 * @param id
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@Override
	public Map<String, Object> updateReceiptAddrStatusSer(String userId, String id) throws Exception {
		result = new HashMap<>();
		long user = 0;
		long receiptId = 0;
		GoodReceiptAddrDo receiptDo = null;
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(id)) {
			user = Long.valueOf(userId);
			receiptId = Long.valueOf(id);
			receiptDo = repository.queryByCondition(user, receiptId);
			if (receiptDo != null) {
				receiptDo.setStatus(1);
				receiptDo.update();
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "修改成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "参数有误");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 修改地址信息
	 * 
	 * @param conditionStr
	 * @return Map<String, Object>
	 * @add by 2016-04-18
	 */
	@Override
	public Map<String, Object> updateReceiptAddrSer(Map<String, Object> paramMap) throws Exception {
		result = new HashMap<>();
		long userId = 0;
		long id = 0;
		GoodReceiptAddrDo receiptDo = null;
		if (paramMap != null) {
			userId = Long.valueOf(paramMap.get("userId") != null ? paramMap.get("userId").toString() : "-1");
			id = Long.valueOf(paramMap.get("id") != null ? paramMap.get("id").toString() : "-1");
			receiptDo = repository.queryByCondition(userId, id);
			if (receiptDo != null) {
				if (paramMap.containsKey("receiptName")) {
					receiptDo.setReceiptName(paramMap.get("receiptName") != null ? paramMap.get("receiptName")
							.toString() : receiptDo.getReceiptName());
				}
				if (paramMap.containsKey("mobile")) {
					String mobile = paramMap.get("mobile").toString();
					if (!CommonUtil.isMobile(mobile)) {
						result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
						result.put(Constants.RESULT_MSG, "手机号码格式有误");
						return result;
					}
					receiptDo.setMobile(mobile);
				}
				if (paramMap.containsKey("fiexdPhone")) {
					String fiexdPhone = paramMap.get("fiexdPhone").toString();
					if (StringUtils.isNotEmpty(fiexdPhone)) {
						if (!CommonUtil.isPhone(fiexdPhone)) {
							result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
							result.put(Constants.RESULT_MSG, "固话号码格式有误");
							return result;
						}
					}
					receiptDo.setFiexdPhone(fiexdPhone);
				}
				if (paramMap.containsKey("receiptEmail")) {
					String receiptEmail = paramMap.get("receiptEmail").toString();
					if (StringUtils.isNotEmpty(receiptEmail)) {
						if (!CommonUtil.checkEmail(receiptEmail)) {
							result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
							result.put(Constants.RESULT_MSG, "邮箱地址格式有误");
							return result;
						}
					}
					receiptDo.setReceiptEmail(receiptEmail);
				}
				if (paramMap.containsKey("detailAddr")) {
					receiptDo.setDetailAddr(paramMap.get("detailAddr") != null ? paramMap.get("detailAddr").toString()
							: receiptDo.getDetailAddr());
				}
				if (paramMap.containsKey("mailbox")) {
					receiptDo.setMailbox(paramMap.get("mailbox") != null ? paramMap.get("mailbox").toString()
							: receiptDo.getMailbox());
				}
				if (paramMap.containsKey("provinceCode")) {
					receiptDo.setProvinceCode(paramMap.get("provinceCode") != null ? paramMap.get("provinceCode")
							.toString() : receiptDo.getProvinceCode());
				}
				if (paramMap.containsKey("provinceName")) {
					receiptDo.setProvinceName(paramMap.get("provinceName") != null ? paramMap.get("provinceName")
							.toString() : receiptDo.getProvinceName());
				}
				if (paramMap.containsKey("cityCode")) {
					receiptDo.setCityCode(paramMap.get("cityCode") != null ? paramMap.get("cityCode").toString()
							: receiptDo.getCityCode());
				}
				if (paramMap.containsKey("areaCode")) {
					receiptDo.setAreaCode(paramMap.get("areaCode") != null ? paramMap.get("areaCode").toString()
							: receiptDo.getAreaCode());
				}
				if (paramMap.containsKey("areaName")) {
					receiptDo.setAreaName(paramMap.get("areaName") != null ? paramMap.get("areaName").toString()
							: receiptDo.getAreaName());
				}
				if (paramMap.containsKey("cityName")) {
					receiptDo.setCityName(paramMap.get("cityName") != null ? paramMap.get("cityName").toString()
							: receiptDo.getCityName());
				}if (paramMap.containsKey("status")) {
					Integer status = paramMap.get("status") != null ? Integer.valueOf(paramMap.get("status").toString()) : 0;
					receiptDo.setStatus(status);
				}
				receiptDo.update();
				result.put(Constants.RESULT_CODE, Constants.RESULT_SUCCESS_CODE);
				result.put(Constants.RESULT_MSG, "修改成功");
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "修改对象不存在");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

}
