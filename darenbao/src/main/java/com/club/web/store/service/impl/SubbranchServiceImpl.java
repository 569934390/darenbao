package com.club.web.store.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.mobile.CommonAppController;
import com.club.web.store.dao.base.po.Subbranch;
import com.club.web.store.dao.base.po.SubbranchGoodSoldout;
import com.club.web.store.dao.extend.StoreLevelExtendMapper;
import com.club.web.store.dao.extend.SubbranchExtendMapper;
import com.club.web.store.dao.extend.SubbranchGoodsSoldoutExtendMapper;
import com.club.web.store.domain.BankCardDo;
import com.club.web.store.domain.StoreLevelDo;
import com.club.web.store.domain.SubbranchDo;
import com.club.web.store.domain.SubbranchGoodsSoldoutDo;
import com.club.web.store.domain.repository.StoreLevelRepository;
import com.club.web.store.domain.repository.SubbranchRepository;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.StoreLevelVo;
import com.club.web.store.vo.SubbranchGoodSoldoutVo;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.SmsUtil;
import com.club.web.weixin.service.WeixinStoreWeixinuserService;
import com.club.web.weixin.vo.WeixinStoreWeixinuserVo;
import com.yaoming.common.util.StringUtil;

@Service("subbranchService")
@Transactional
public class SubbranchServiceImpl implements SubbranchService {

	@Autowired
	SubbranchExtendMapper subbranchExtendMapper;
	@Autowired
	SubbranchGoodsSoldoutExtendMapper subbranchGoodsSoldoutExtendMapper;
	@Autowired
	StoreLevelExtendMapper storeLevelExtendMapper;
	@Autowired
	WeixinStoreWeixinuserService weixinStoreWeixinuserService;
	@Autowired
	StoreLevelRepository storeLevelRepository;
	@Autowired
	SubbranchRepository repository;
	@Autowired
	SmsUtil smsUtil;
	@Autowired
	TradeHeadStoreService tradeHeadStoreService;

	/**
	 * @Title: saveOrUpdateSubbranch
	 * @Description: 后台创建和修改店铺
	 * @param subbranchVo
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@Override
	public Map<String, Object> saveOrUpdateSubbranch(SubbranchVo subbranchVo, HttpServletRequest request)
			throws NoSuchAlgorithmException {
		Map<String, Object> map =new HashMap<>();
		int checkNumberN = 0;
		if (!StringUtils.isEmpty(subbranchVo.getNumber())&&!StringUtils.isEmpty(subbranchVo.getDepartmentNum())) {
			map.put("number", subbranchVo.getNumber());
			
			checkNumberN = subbranchExtendMapper.checkNumber(map);
		}
		int checkLoginNameN = 0;
		if (!StringUtils.isEmpty(subbranchVo.getMobile())) {
			Assert.isTrue(CommonUtil.isMobile(subbranchVo.getMobile()), "请输入正确的手机账号");
			checkLoginNameN = subbranchExtendMapper.checkLoginName(subbranchVo.getMobile());
		} else {
			Assert.isTrue(false, "请输入手机账号");
		}
		if (!StringUtils.isEmpty(subbranchVo.getPhone())) {
			Assert.isTrue(CommonUtil.isDigital(subbranchVo.getPhone()),"请输入正确的门店电话");
		}
		Map<String, Object> result = new HashMap<String, Object>();

		SubbranchDo subbranchDo = new SubbranchDo();

		BeanUtils.copyProperties(subbranchVo, subbranchDo);

		Long storeId = tradeHeadStoreService.getStaffHeadStoreId(request);
		if (storeId != null) {
			subbranchDo.setStoreId(storeId);
		}
		if (!StringUtil.isEmpty(subbranchVo.getCreateBy())) {
			subbranchDo.setCreateBy(Long.parseLong(subbranchVo.getCreateBy()));
		}
		Assert.isTrue(!StringUtil.isEmpty(subbranchVo.getLevelId()), "店铺等级没选");
		subbranchDo.setLevelId(Long.parseLong(subbranchVo.getLevelId()));

		if (!StringUtil.isEmpty(subbranchVo.getId())) {
			subbranchDo.setId(Long.parseLong(subbranchVo.getId()));
		}
		int resultint;
		if (subbranchDo.getId() != null) {
			SubbranchVo oldsubbranchVo = selectByPrimaryKey(subbranchDo.getId());
			Assert.isTrue((checkNumberN == 1&&subbranchDo.getNumber().equals(oldsubbranchVo.getNumber()))||checkNumberN == 0, "店铺编号已存在");
			Assert.isTrue((checkLoginNameN == 1&&subbranchDo.getMobile().equals(oldsubbranchVo.getMobile()))||checkLoginNameN == 0, "店铺手机号已存在");

			subbranchDo.setUpdateTime(new Date());
			resultint = subbranchDo.update();
		} else {
			Assert.isTrue(checkNumberN == 0, "店铺编号已存在");
			Assert.isTrue(checkLoginNameN == 0, "店铺手机号已存在");
			subbranchDo.setCreateTime(new Date());
			resultint = subbranchDo.insert();
		}
		result.put("msg", "修改成功");
		result.put("success", true);
		return result;
	}

	/**
	 * @Title: selectByPrimaryKey
	 * @Description: 根据主键查询店铺
	 * @param id
	 * @return
	 */
	@Override
	public SubbranchVo selectByPrimaryKey(Long id) {

		SubbranchVo subbranchVo;
		Subbranch subbranch = subbranchExtendMapper.selectByPrimaryKey(id);
		if (subbranch != null) {
			subbranchVo = new SubbranchVo();
			BeanUtils.copyProperties(subbranch, subbranchVo);
			subbranchVo.setId(subbranch.getId() + "");
			subbranchVo.setStoreId(subbranch.getStoreId() + "");
			subbranchVo.setCreateBy(subbranch.getCreateBy() + "");
			subbranchVo.setLevelId(subbranch.getLevelId() + "");
			subbranchVo.setNumber(subbranch.getNumber());
			subbranchVo.setDepartmentNum(subbranch.getDepartmentNum());
			subbranchVo.setMobile(subbranch.getMobile());
			if("-1".equals(subbranch.getCountry())){
				subbranchVo.setCountry(null);
			}
			StoreLevelDo storeLevelDo = storeLevelRepository.getStoreLevelDoByLevelId(subbranch.getLevelId());
			if (storeLevelDo != null) {
				subbranchVo.setLevelName(storeLevelDo.getName());
			}
		} else {
			subbranchVo = null;
		}
		return subbranchVo;
	}

	/**
	 * getSubbranchList
	 * 
	 * @Description: 后台店铺高级查询管理
	 * @param page
	 * @return Page
	 */
	@Override
	public Page getSubbranchList(Page page) {

		String Condition = "", loginName = "";
		try {
			if (page.getConditons() != null) {
				Condition = page.getConditons().get("Condition").toString();
				loginName = page.getConditons().get("loginName").toString();
			}
		} catch (Exception e) {

		}

		int total = subbranchExtendMapper.selectByPageCount(Condition, loginName);
		List<Subbranch> SubbranchList = new ArrayList<>();
		if (total > page.getLimit()) {
			SubbranchList = subbranchExtendMapper.selectByPage(Condition, loginName, page.getStart(), page.getLimit());
		} else {
			SubbranchList = subbranchExtendMapper.selectByPage(Condition, loginName, page.getStart(), total);
		}

		page.setTotalRecords(total);
		List<SubbranchVo> subbranchVoList = null;
		SubbranchVo subbranchVo;
		List<StoreLevelVo> levelVoList = null;
		try {
			levelVoList = storeLevelExtendMapper.findAllStoreLevel();
		} catch (Exception e) {

		}
		if (SubbranchList.size() != 0) {
			subbranchVoList = new ArrayList<>();
			for (Subbranch subbranch : SubbranchList) {
				subbranchVo = new SubbranchVo();
				BeanUtils.copyProperties(subbranch, subbranchVo);
				subbranchVo.setId(subbranch.getId() + "");
				subbranchVo.setStoreId(subbranch.getStoreId() + "");
				subbranchVo.setCreateBy(subbranch.getCreateBy() + "");
				subbranchVo.setLevelId(subbranch.getLevelId() + "");
				subbranchVo.setDepartmentNum(subbranch.getDepartmentNum());
				subbranchVoList.add(subbranchVo);

			}
			page.setResultList(subbranchVoList);
		}

		return page;
	}

	/**
	 * 
	 * updateSubbranchState
	 * 
	 * @Description: 更新店铺状态，启用还是禁用
	 * @param SubbranchIds
	 * @param action
	 * @return int
	 * @date 2016年6月8日
	 */
	@Override
	public int updateSubbranchState(String[] SubbranchIds, String action) {
		SubbranchDo SubbranchDo = new SubbranchDo();
		int result = 0;
		try {
			if (action != null) {
				for (String SubbranchId : SubbranchIds) {
					if (SubbranchId != null && SubbranchId != "") {
						SubbranchDo.setState(Integer.parseInt(action));
						SubbranchDo.setId(Long.parseLong(SubbranchId));
						SubbranchDo.updateSubbranchState();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 * saveSubbranchGoodsSoldout
	 * 
	 * @Description: 下架某些不想要的商品
	 * @param subbranchGoodSoldoutVo
	 * @return Map<String, Object>
	 * @date 2016年6月8日
	 */
	@Override
	public Map<String, Object> saveSubbranchGoodsSoldout(SubbranchGoodSoldoutVo subbranchGoodSoldoutVo) {
		SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo = new SubbranchGoodsSoldoutDo();
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (!StringUtils.isEmpty(subbranchGoodSoldoutVo.getSubranchId())) {
				subbranchGoodsSoldoutDo.setSubranchId(Long.parseLong(subbranchGoodSoldoutVo.getSubranchId()));
			}
			String[] goodStrings = subbranchGoodSoldoutVo.getGoodId().split(",");

			for (String goodString : goodStrings) {
				if (!StringUtils.isEmpty(subbranchGoodSoldoutVo.getGoodId())) {
					subbranchGoodsSoldoutDo.setGoodId(Long.parseLong(goodString));
					subbranchGoodsSoldoutDo.insert();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", e.getMessage());
		}

		return result;
	}

	/**
	 * 
	 * getSubbranchGoodsSoldoutList
	 * 
	 * @Description: 根据ID获取当前店铺所有下架商品列表
	 * @param page
	 * @return Page
	 * @date 2016年6月8日
	 */

	@Override
	public Page getSubbranchGoodsSoldoutList(Page page) {
		String subbranchIdString = "";
		List<SubbranchGoodSoldout> subbranchGoodsSoldoutList = null;
		List<SubbranchGoodSoldoutVo> subbranchGoodsSoldoutVoList = null;

		int total = 0;
		if (page.getConditons() != null) {
			subbranchGoodsSoldoutList = new ArrayList<SubbranchGoodSoldout>();
			subbranchGoodsSoldoutVoList = new ArrayList<SubbranchGoodSoldoutVo>();

			subbranchIdString = page.getConditons().get("subbranchId").toString();
			if (!StringUtils.isEmpty(subbranchIdString)) {
				Long subbranchId = Long.parseLong(subbranchIdString);
				total = subbranchGoodsSoldoutExtendMapper.selectBySubbranchIdCount(subbranchId);
				subbranchGoodsSoldoutList = subbranchGoodsSoldoutExtendMapper.selectBySubbranchIdList(subbranchId,
						page.getStart(), page.getLimit());

			}

			for (SubbranchGoodSoldout subbranchGoodSoldout : subbranchGoodsSoldoutList) {
				SubbranchGoodSoldoutVo subbranchGoodSoldoutVo = new SubbranchGoodSoldoutVo();
				subbranchGoodSoldoutVo.setId(subbranchGoodSoldout.getId() + "");
				subbranchGoodSoldoutVo.setCreateTime(subbranchGoodSoldout.getCreateTime());
				subbranchGoodSoldoutVo.setGoodId(subbranchGoodSoldout.getGoodId() + "");
				subbranchGoodSoldoutVo.setSubranchId(subbranchGoodSoldout.getSubranchId() + "");
				subbranchGoodsSoldoutVoList.add(subbranchGoodSoldoutVo);
			}
		}
		page.setTotalRecords(total);
		page.setResultList(subbranchGoodsSoldoutVoList);
		return page;
	}

	/**
	 * 
	 * deleteSubbranchGoodsSoldout
	 * 
	 * @Description: 删除下架商品
	 * @param subbranchId
	 * @param goodIds
	 * @return
	 * @date 2016年6月8日
	 */
	@Override
	public int deleteSubbranchGoodsSoldout(Long subbranchId, String goodIds) {
		SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo = new SubbranchGoodsSoldoutDo();
		int result = 0;
		if (subbranchId != null) {
			subbranchGoodsSoldoutDo.setSubranchId(subbranchId);
			if (!StringUtils.isEmpty(goodIds)) {
				String[] goodIdList = goodIds.split(",");
				for (String goodId : goodIdList) {
					if (!StringUtils.isEmpty(goodId)) {
						subbranchGoodsSoldoutDo.setGoodId(Long.parseLong(goodId));
						result = subbranchGoodsSoldoutDo.delete();
					}
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * deleteSubbranch
	 * 
	 * @Description: 删除分店
	 * @param subbranchIds
	 * @return
	 * @date 2016年6月8日
	 */
	@Override
	public int deleteSubbranch(String subbranchIds) {
		SubbranchDo subbranchDo = new SubbranchDo();
		SubbranchGoodsSoldoutDo subbranchGoodsSoldoutDo = new SubbranchGoodsSoldoutDo();
		BankCardDo bankCardDo = new BankCardDo();
		int result = 0;

		if (!StringUtils.isEmpty(subbranchIds)) {
			String[] subbranchIdList = subbranchIds.split(",");
			for (String subbranchId : subbranchIdList) {
				if (!StringUtils.isEmpty(subbranchId)) {
					subbranchGoodsSoldoutDo.setSubranchId(Long.parseLong(subbranchId));
					subbranchGoodsSoldoutDo.delete();

					bankCardDo.setConnectId(Long.parseLong(subbranchId));
					bankCardDo.deletBySubranchID();

					WeixinStoreWeixinuserVo weixinStoreWeixinuserVo = new WeixinStoreWeixinuserVo();
					weixinStoreWeixinuserVo.setStoreId(subbranchId);
					weixinStoreWeixinuserService.delete(weixinStoreWeixinuserVo);

					subbranchDo.setId(Long.parseLong(subbranchId));
					result = subbranchDo.delete();
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * getSubbranceName
	 * 
	 * @Description: 获取所有店铺名称
	 * @param page
	 * @return
	 * @date 2016年6月8日
	 */

	@Override
	public Page getSubbranceName(Page page) {
		String Condition = "";
		try {
			if (page.getConditons() != null) {
				Condition = page.getConditons().get("Condition").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Subbranch> SubbranchList = new ArrayList<>();
		SubbranchList = subbranchExtendMapper.getSubbranchName(Condition, page.getStart(), page.getLimit());

		List<SubbranchVo> subbranchVoList = null;
		SubbranchVo subbranchVo = null;

		if (SubbranchList.size() != 0) {
			subbranchVoList = new ArrayList<>();
			for (Subbranch subbranch : SubbranchList) {
				subbranchVo = new SubbranchVo();
				BeanUtils.copyProperties(subbranch, subbranchVo);
				subbranchVo.setId(subbranch.getId() + "");
				subbranchVoList.add(subbranchVo);
			}
		}

		page.setResultList(subbranchVoList);
		return page;
	}

	/**
	 * 
	 * selectByNumber
	 * 
	 * @Description: 根据店铺编号获取店铺名称
	 * @param number
	 * @return
	 * @date 2016年6月8日
	 */

	@Override
	public SubbranchVo selectByNumber(String number) {

		SubbranchVo subbranchVo = null;
		Subbranch subbranch = null;
		subbranch = subbranchExtendMapper.selectByNumber(number);
		if (subbranch != null) {
			subbranchVo = new SubbranchVo();
			subbranchVo.setId(subbranch.getId().toString());
		}

		return subbranchVo;

	}

	/**
	 * 查询店铺协议
	 * 
	 * @param shopId
	 * @return String
	 * @add by 2016-05-16
	 */
	@Override
	public String getShopProtocolSer(long shopId) {
		String text = subbranchExtendMapper.getShopProtocol(shopId);
		return text;
	}

	/**
	 * 修改我的资料
	 * 
	 * @param paramStr
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	@Override
	public Map<String, Object> updateUserMsgSer(Map<String, Object> paramStr) {
		Map<String, Object> result = new HashMap<>();
		long shopId = 0;
		SubbranchDo shopObj = null;
		if (paramStr != null) {
			if (paramStr.containsKey("shopId")) {
				shopId = paramStr.get("shopId") != null ? Long.valueOf(paramStr.get("shopId").toString()) : 0;
				shopObj = subbranchExtendMapper.getSubbranchById(shopId);
				if (shopObj != null) {
					if (paramStr.containsKey("phone")) {
						shopObj.setPhone(paramStr.get("phone") != null ? paramStr.get("phone").toString() : shopObj
								.getPhone());
					}
					if (paramStr.containsKey("headUrl")) {
						shopObj.setHeadImgUrl(paramStr.get("headUrl") != null ? paramStr.get("headUrl").toString()
								: shopObj.getHeadImgUrl());
					}
					if (paramStr.containsKey("name")) {
						shopObj.setName(paramStr.get("name") != null ? paramStr.get("name").toString() : shopObj
								.getName());
					}
					if (paramStr.containsKey("userName")) {
						shopObj.setUserName(paramStr.get("userName") != null ? paramStr.get("userName").toString()
								: shopObj.getUserName());
					}
					if (paramStr.containsKey("weixin")) {
						shopObj.setWeixin(paramStr.get("weixin") != null ? paramStr.get("weixin").toString() : shopObj
								.getWeixin());
					}
					if (paramStr.containsKey("province")) {
						shopObj.setProvince(paramStr.get("province") != null ? paramStr.get("province").toString()
								: shopObj.getProvince());
					}
					if (paramStr.containsKey("provinceName")) {
						shopObj.setProvinceName(paramStr.get("provinceName") != null ? paramStr.get("provinceName")
								.toString() : shopObj.getProvinceName());
					}
					if (paramStr.containsKey("city")) {
						shopObj.setCity(paramStr.get("city") != null ? paramStr.get("city").toString() : shopObj
								.getCity());
					}
					if (paramStr.containsKey("cityName")) {
						shopObj.setCityName(paramStr.get("cityName") != null ? paramStr.get("cityName").toString()
								: shopObj.getCityName());
					}
					if (paramStr.containsKey("country")) {
						shopObj.setCountry(paramStr.get("country") != null ? paramStr.get("country").toString()
								: shopObj.getCountry());
					} else{
						shopObj.setCountry("-1");
					}
					if (paramStr.containsKey("countryName")) {
						shopObj.setCountryName(paramStr.get("countryName") != null ? paramStr.get("countryName")
								.toString() : shopObj.getCountryName());
					} else{
						shopObj.setCountryName("");
					}
					if (paramStr.containsKey("description")) {
						shopObj.setDescription(paramStr.get("description") != null ? paramStr.get("description")
								.toString() : shopObj.getDescription());
					}
					if (paramStr.containsKey("addres")) {

						shopObj.setAddress(paramStr.get("addres") != null ? paramStr.get("addres").toString() : shopObj
								.getAddress());
					}
					shopObj.update();
					result.put(Constants.RESULT_CODE, "0");
					result.put(Constants.RESULT_MSG, "修改成功");
				} else {
					result.put(Constants.RESULT_CODE, "1");
					result.put(Constants.RESULT_MSG, "参数为空");
				}
			} else {
				result.put(Constants.RESULT_CODE, "1");
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	@Override
	public Map<String, Object> login(String loginName, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (loginName != null) {
			loginName = loginName.trim();
			if (!Pattern.matches("(^[a-zA-Z0-9]$)|(^[a-zA-Z0-9]+[a-zA-Z0-9_]*[a-zA-Z0-9]+$)", loginName)) {
				result.put("code", "1");
				result.put("type", 1);
				result.put("msg", "用户名只能数字、字母、下划线（禁止首尾）");
				return result;
			}
		}
		if (password != null) {
			password = password.trim();
			if (!Pattern.matches("^[a-zA-Z0-9`\\-\\=\\\\\\[\\];',.\\/\\~\\!@#$%\\^&\\*()_\\+|\\?><\":\\{\\}]*",
					password)) {
				result.put("code", "1");
				result.put("type", 1);
				result.put("msg",
						"密码：包含字母（A-Z）大小写敏感；数字（0-9）；特殊字符（`、-、=、\\、[、]、;、'、,、.、/、~、!、@、#、$、%、^、&、*、(、)、_、+、|、?、>、<、\"、:、{、}）");
				return result;
			}
		}
		List<SubbranchVo> subbranchVoList = subbranchExtendMapper.findByLoginNameAndPass(loginName, password);
		if (subbranchVoList == null || subbranchVoList.size() == 0) {
			result.put("code", "1");
			result.put("msg", "账号或密码错误！");
		} else {
			result.put("code", "0");
			result.put("msg", subbranchVoList.get(0));
		}
		return result;
	}

	private String verifyCode(String phoneCode) {
		String verifyCode = StringUtils.random(4);
		CommonAppController.phoneCodeCache.put(phoneCode, verifyCode);
		CommonAppController.phoneCodeCache.put(phoneCode + "_time", new Date().getTime() + "");
		return verifyCode;
	}

	@Override
	public Map<String, Object> verifyPhone(String phoneCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(phoneCode)) {
			String verifyCode = "";
			if (CommonAppController.phoneCodeCache.containsKey(phoneCode)) {
				long delay = Long.parseLong(CommonAppController.phoneCodeCache.get(phoneCode + "_time"));
				if (new Date().getTime() - delay > 3600 * 1000) {
					verifyCode = this.verifyCode(phoneCode);
				} else {
					verifyCode = CommonAppController.phoneCodeCache.get(phoneCode);
				}
			} else {
				verifyCode = this.verifyCode(phoneCode);
			}
			String resultSms = smsUtil.sendRegistVerifyCode(phoneCode, verifyCode);
			if (resultSms != null) {
				result.put("success", true);
				result.put("resultData", resultSms);
				result.put("msg", "验证码已发送至手机");
				result.put("timeout", 60000);
			} else {
				result.put("success", false);
				result.put("msg", "发送失效！");
			}
		} else {
			result.put("success", false);
			result.put("msg", "电话号码无效！");
		}
		return result;
	}

	@Override
	public Map<String, Object> resetPassword(String verifyCode, String mobile, String password, String repassword,
			HttpServletRequest request) throws NoSuchAlgorithmException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!password.equals(repassword)) {
			result.put("msg", "两次密码不一致！");
			result.put("success", false);
			return result;
		} else if (!CommonAppController.phoneCodeCache.containsKey(mobile)) {
			result.put("success", false);
			result.put("msg", "还没收到短信验证码？请重新发送");
			return result;
		} else if (!CommonAppController.phoneCodeCache.get(mobile).equals(verifyCode)) {
			result.put("msg", "短信验证码错误");
			result.put("success", false);
			return result;
		} else {
			List<SubbranchVo> subbranchVoList = selectByMobile(mobile);
			if (subbranchVoList == null || subbranchVoList.size() == 0) {
				result.put("success", false);
				result.put("msg", "该手机号还没有注册分店！");
			} else {
				SubbranchVo subbranchVo = subbranchVoList.get(0);
				subbranchVo.setPassword(repassword);
				result.put("success", true);
				result.put("msg", saveOrUpdateSubbranch(subbranchVo, request));
			}
		}
		return result;
	}

	public List<SubbranchVo> selectByMobile(String mobile) {
		return subbranchExtendMapper.selectByMobile(mobile);
	}

	/**
	 * 查询商品是否上下架
	 * 
	 * @param goodId
	 * @return int
	 * @add by 2016-05-18
	 */
	@Override
	public int getShopGoodStatus(long goodId, long shopId) {
		return subbranchGoodsSoldoutExtendMapper.getShopGoodStatus(goodId, shopId);
	}

	/**
	 * 店铺商品上架
	 * 
	 * @param shopId
	 * @param goodIds
	 * @return Map<String, String>
	 * @add by 2016-05-18
	 */
	@Override
	public Map<String, Object> setGoodShelveSer(String shopId, String goodIds) {
		Map<String, Object> result = new HashMap<>();
		long shop = 0;
		if (StringUtils.isNotEmpty(shopId) && StringUtils.isNotEmpty(goodIds)) {
			shop = Long.valueOf(shopId);
			String[] arr = goodIds.split(",");
			if (arr != null && arr.length > 0) {
				SubbranchGoodsSoldoutDo obj = null;
				for (int i = 0; i < arr.length; i++) {
					obj = repository.create(shop, Long.valueOf(arr[i]));
					obj.save();
				}
				result.put(Constants.RESULT_CODE, "0");
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, "1");
				result.put(Constants.RESULT_MSG, "goodIds参数为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 店铺商品下架
	 * 
	 * @param shopId
	 * @param goodIds
	 * @return Map<String, String>
	 * @add by 2016-05-18
	 */
	@Override
	public Map<String, Object> setGoodOutShelveSer(String shopId, String goodIds) {
		Map<String, Object> result = new HashMap<>();
		long shop = 0;
		List<Long> list = null;
		if (StringUtils.isNotEmpty(shopId) && StringUtils.isNotEmpty(goodIds)) {
			shop = Long.valueOf(shopId);
			String[] arr = goodIds.split(",");
			if (arr != null && arr.length > 0) {
				list = new ArrayList<Long>();
				for (int i = 0; i < arr.length; i++) {
					list.add(Long.valueOf(arr[i]));
				}
				subbranchGoodsSoldoutExtendMapper.deleteSubbranchGoodsSoldoutByCondition(shop, list);
				result.put(Constants.RESULT_CODE, "0");
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, "1");
				result.put(Constants.RESULT_MSG, "goodIds参数为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * getSubbranchListForActivity
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public List<SubbranchVo> getSubbranchListForActivity() {
		return subbranchExtendMapper.getSubbranchListForActivity();
	}

}
