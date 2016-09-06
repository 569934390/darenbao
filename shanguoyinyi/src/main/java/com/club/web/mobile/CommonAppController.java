package com.club.web.mobile;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.coupon.service.CouponDetailService;
import com.club.web.coupon.service.CouponService;
import com.club.web.coupon.vo.CouponDetailVo;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.deal.service.IndentService;
import com.club.web.image.service.HomePageImgService;
import com.club.web.spread.service.SpreadClassifyService;
import com.club.web.spread.service.SpreadManagerService;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.spread.vo.MarketSpreadManagerVo;
import com.club.web.spread.vo.SpreadVo;
import com.club.web.store.service.BankCardService;
import com.club.web.store.service.PushMessageService;
import com.club.web.store.service.SettlementBillService;
import com.club.web.store.service.SubbranchService;
import com.club.web.store.vo.BankCardExtendVo;
import com.club.web.store.vo.BankCardVo;
import com.club.web.store.vo.SubbranchVo;
import com.club.web.weixin.service.WeixinUserInfoService;
import com.club.web.weixin.vo.FansMsgVo;
import com.yaoming.common.util.StringUtil;

/**
 * 三国公共控制类-Controller
 * 
 * @author wqh
 * @add by 2016-05-12
 */
@RequestMapping("/mobile")
@Controller
public class CommonAppController {

	private Logger logger = LoggerFactory.getLogger(CommonAppController.class);

	public static Map<String, String> phoneCodeCache = new HashMap<String, String>();

	@Autowired
	SubbranchService subbranchService;
	@Autowired
	PushMessageService pushMsg;
	@Autowired
	BankCardService bankCard;
	@Autowired
	WeixinUserInfoService fans;
	@Autowired
	CouponService voucher;
	@Autowired
	CouponDetailService voucherDeatil;
	@Autowired
	SubbranchService branchService;
	@Autowired
	SettlementBillService billService;
	@Autowired
	IndentService indentService;
	@Autowired
	SpreadClassifyService spreadClassifyService;
	@Autowired
	SpreadManagerService spreadManagerService;
	@Autowired
	HomePageImgService homePageImgService;
	private Map<String, Object> result;
    
	/**
	 * B端获取店铺信息
	 * 
	 * @param id
	 * @param response
	 * @return
	 */
	@RequestMapping("/store/subbranch/loadInfo")
	@ResponseBody
	public SubbranchVo findSubbranchById(Long shopId, HttpServletResponse response) {
		logger.debug("findSubbranchById GoodWeixinController ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return subbranchService.selectByPrimaryKey(shopId);
		} catch (Exception e) {
			logger.error("cargoclassify queryByParentId error:", e);
		}
		return null;
	}

	/**
	 * 查看银行卡列表
	 * 
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<BankCardExtendVo>
	 * @add by 2016-05-12
	 */
	@RequestMapping("/user/getBankCardList")
	@ResponseBody
	public List<BankCardExtendVo> getBankCardListCon(String shopId, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<BankCardExtendVo> list = null;
		try {
			list = bankCard.getBankCardListSer(shopId, pageSize, pageNum);
		} catch (Exception e) {
			logger.error("查看银行卡列表异常<getBankCardList>:", e);
		}
		return list;
	}

	/**
	 * 查看银行卡列表根据Id
	 * 
	 * @param id
	 * @param pageSize
	 * @param pageNum
	 * @return List<BankCardExtendVo>
	 * @add by 2016-05-12
	 */
	@RequestMapping("/user/getBankCardObj")
	@ResponseBody
	public BankCardExtendVo getBankCardObjCon(String id, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		BankCardExtendVo obj = null;
		try {
			obj = bankCard.getBankCardSer(id);
		} catch (Exception e) {
			logger.error("查看银行卡列表异常<getBankCardList>:", e);
		}
		return obj;
	}

	/**
	 * 添加修改银行卡
	 * 
	 * @param param
	 * @return Map<String,String>
	 * @add by 2016-05-12
	 */
	@RequestMapping("/user/saveBankMsg")
	@ResponseBody
	public Map<String, Object> saveBankMsgCon(String param, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		result = new HashMap<String, Object>();
		BankCardExtendVo obj = null;
		BankCardVo bankCardVo = null;
		try {
			if (StringUtils.isNotEmpty(param)) {
				obj = JsonUtil.toBean(param, BankCardExtendVo.class);
				bankCardVo = new BankCardVo();
				bankCardVo.setIdCard(StringUtils.isNotEmpty(obj.getCard()) ? obj.getCard() : StringUtils.EMPTY);
				bankCardVo.setConnectId(StringUtils.isNotEmpty(obj.getShopId()) ? Long.valueOf(obj.getShopId()) : 0);
				bankCardVo.setName(StringUtils.isNotEmpty(obj.getName()) ? obj.getName() : StringUtils.EMPTY);
				if (StringUtils.isNotEmpty(obj.getId())) {
					bankCardVo.setBankCardId(Long.valueOf(obj.getId()));
				}
				bankCardVo.setBankCard(StringUtils.isNotEmpty(obj.getBankCard()) ? obj.getBankCard()
						: StringUtils.EMPTY);
				bankCardVo.setBankName(StringUtils.isNotEmpty(obj.getBankName()) ? obj.getBankName()
						: StringUtils.EMPTY);
				bankCardVo.setBankAddress(StringUtils.isNotEmpty(obj.getBankAddress()) ? obj.getBankAddress()
						: StringUtils.EMPTY);
				bankCardVo.setMobile(StringUtils.isNotEmpty(obj.getMobile()) ? obj.getMobile() : StringUtils.EMPTY);
				bankCardVo.setConnectType(1);
				bankCard.saveOrUpdateBankCard(bankCardVo);
				result.put(Constants.RESULT_CODE, "0");
				result.put(Constants.RESULT_MSG, "操作成功");
			} else {
				result.put(Constants.RESULT_CODE, "1");
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, e.getMessage());
			logger.error("添加修改银行卡异常<saveBankMsgCon>:", e);
		}
		return result;
	}

	/**
	 * 查看店铺的粉丝列表
	 * 
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<FansMsgVo>
	 * @add by 2016-05-12
	 */
	@RequestMapping("/fans/getFansList")
	@ResponseBody
	public List<FansMsgVo> getFansListCon(String shopId, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<FansMsgVo> list = null;
		try {
			if (pageNum < 1) {
				pageNum = 1;
			}
			if (pageSize < 1) {
				pageSize = 6;
			}
			list = fans.getFansListSer(shopId, (pageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			logger.error("查看店铺的粉丝列表异常<getFansListCon>:", e);
		}
		return list;
	}

	/**
	 * 查询卡劵列表
	 * 
	 * @param shopId
	 * @param pageSize
	 * @param pageNum
	 * @return List<FansMsgVo>
	 * @add by 2016-05-13
	 */
	@RequestMapping("/voucher/getVoucherList")
	@ResponseBody
	public List<CouponVo> getVoucherListCon(String shopId, @RequestParam(defaultValue = "6") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<CouponVo> list = null;
		try {
			if (pageNum < 1) {
				pageNum = 1;
			}
			if (pageSize < 1) {
				pageSize = 6;
			}
			list = voucher.getVoucherListSer(shopId, (pageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			logger.error("查询卡劵列表异常<getVoucherListCon>:", e);
		}
		return list;
	}

	/**
	 * 查询卡劵明细列表
	 * 
	 * @param vocherId
	 * @param status
	 * @param pageSize
	 * @param pageNum
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	@RequestMapping("/voucher/getVoucherDetailList")
	@ResponseBody
	public List<CouponDetailVo> getVoucherDetailListCon(String vocherId, @RequestParam(defaultValue = "1") int status,
			@RequestParam(defaultValue = "6") int pageSize, @RequestParam(defaultValue = "1") int pageNum,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<CouponDetailVo> list = null;
		try {
			if (pageNum < 1) {
				pageNum = 1;
			}
			if (pageSize < 1) {
				pageSize = 6;
			}
			list = voucherDeatil.getVoucherDetailListSer(vocherId, status, (pageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			logger.error("查询卡劵列表异常<getVoucherDetailListCon>:", e);
		}
		return list;
	}

	/**
	 * 搜索卡劵明细列表
	 * 
	 * @param code
	 * @param pageSize
	 * @param pageNum
	 * @return List<CouponDetailVo>
	 * @add by 2016-05-13
	 */
	@RequestMapping("/voucher/searchVoucherDetailList")
	@ResponseBody
	public List<CouponDetailVo> searchVoucherDetailListCon(@RequestParam(defaultValue = "") String code,
			@RequestParam(defaultValue = "6") int pageSize, @RequestParam(defaultValue = "1") int pageNum,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<CouponDetailVo> list = null;
		try {
			if (pageNum < 1) {
				pageNum = 1;
			}
			if (pageSize < 1) {
				pageSize = 6;
			}
			list = voucherDeatil.getVoucherDetailListSer(code, (pageNum - 1) * pageSize, pageSize);
		} catch (Exception e) {
			logger.error("搜索卡劵明细列表异常<searchVoucherDetailListCon>:", e);
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
	@RequestMapping("/voucher/setVoucherStatus")
	@ResponseBody
	public Map<String, Object> setVoucherStatusCon(String vouchId, @RequestParam(defaultValue = "3") int status,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		result = new HashMap<>();
		try {
			long vouch = Long.valueOf(vouchId);
			result = voucherDeatil.setVoucherStatusSer(vouch, status);
		} catch (Exception e) {
			logger.error("设置卡劵禁用异常<setVoucherStatusCon>:", e);
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, e.getMessage());
		}
		return result;
	}

	/**
	 * 查询店铺协议
	 * 
	 * @param shopId
	 * @return String
	 * @add by 2016-05-16
	 */
	@RequestMapping("/shop/getShopProtocol")
	@ResponseBody
	public String getShopProtocolCon(String shopId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		String text = StringUtils.EMPTY;
		try {
			long shop = Long.valueOf(shopId);
			text = branchService.getShopProtocolSer(shop);
		} catch (Exception e) {
			logger.error("查询店铺协议异常<getShopProtocolCon>:", e);
		}
		return text;
	}

	/**
	 * 查询我的账户
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	@RequestMapping("/user/getUserMsg")
	@ResponseBody
	public Map<String, Object> getUserMsgCon(String shopId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			result = billService.getShopBill(Long.valueOf(shopId));
		} catch (Exception e) {
			logger.error("查询我的账户异常<getUserMsgCon>:", e);
			result.put("settled", "0.00");
			result.put("canSettled", "0.00");
			result.put("noSettled", "0.00");
		}
		return result;
	}

	/**
	 * 修改我的资料
	 * 
	 * @param param
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	@RequestMapping("/user/updateUserMsg")
	@ResponseBody
	public Map<String, Object> updateUserMsgCon(String param, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		result = new HashMap<>();
		try {
			Map<String, Object> paramStr = null;
			if (StringUtils.isNotEmpty(param)) {
				paramStr = JsonUtil.toMap(param);
				result = branchService.updateUserMsgSer(paramStr);
			} else {
				result.put(Constants.RESULT_CODE, "1");
				result.put(Constants.RESULT_MSG, "参数为空");
			}
		} catch (Exception e) {
			logger.error("修改我的资料异常<updateUserMsgCon>:", e);
			result.put(Constants.RESULT_CODE, "1");
			result.put(Constants.RESULT_MSG, e.getMessage());
		}
		return result;
	}

	/**
	 * 查询店铺订单统计值
	 * 
	 * @param shopId
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	@RequestMapping("/user/getOrderTotalMsg")
	@ResponseBody
	public Map<String, Object> getOrderTotalMsgCon(String shopId, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		result = new HashMap<String, Object>();
		try {
			result = indentService.getOrderTotalMsgSer(Long.valueOf(shopId));
		} catch (Exception e) {
			logger.error("查询店铺订单统计值异常<getOrderTotalMsgCon>:", e);
			result.put("historyNumTotal", "0");
			result.put("hostoryAmtTotal", "0.00");
			result.put("todayNumTotal", "0");
			result.put("tomorowAmtTotal", "0.00");
		}
		return result;
	}

	/**
	 * 登录
	 * 
	 * @param loginName
	 * @param password
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public Map<String, Object> login(String loginName, String password, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<>();
		try {
			result.putAll(branchService.login(loginName, password));
		} catch (Exception e) {
			result.put("code", "1");
			result.put("msg", "登录失败！");
			logger.error("登录异常<login>:", e);
		}
		return result;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param phoneCode
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/user/verifyPhone")
	@ResponseBody
	public Map<String, Object> verifyPhone(String phoneCode, HttpServletResponse response)
			throws UnsupportedEncodingException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(branchService.verifyPhone(phoneCode));
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "获取验证码失败！");
			logger.error("获取验证码<verifyPhone>:", e);
		}
		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param verifyCode
	 * @param mobile
	 * @param password
	 * @param repassword
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/resetPassword")
	@ResponseBody
	public Map<String, Object> resetPassword(@RequestParam(value = "verifyCode", required = true) String verifyCode,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "repassword", required = true) String repassword, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("resetPassword ");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.putAll(branchService.resetPassword(verifyCode, mobile, password, repassword, request));
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("获取验证码<verifyPhone>:", e);
		}
		return result;
	}

	/**
	 * 查询所有启用的推广分类
	 * 
	 * @param
	 * @return 所有分类的list
	 */
	@RequestMapping("/findAllSpreadClassifyOnStatus")
	@ResponseBody
	public List<MarketSpreadClassifyVo> findAllSpreadClassifyOnStatus(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		try {
			return spreadClassifyService.findAllClassifyOnStatus();
		} catch (Exception e) {
			logger.error("查询所有启用的推广分类数量异常:", e);
		}
		return null;
	}

	/**
	 * 获取推广列表
	 * 
	 * @param conditionStr
	 * @param start
	 * @param limit
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSpreadList")
	@ResponseBody
	public List<SpreadVo> getSpreadList(String conditionStr, Integer start, Integer limit, HttpServletResponse response) {
		logger.debug(" spreadManagerController getSpreadList");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		List<SpreadVo> list = null;
		try {
			Map<String, Object> map = StringUtils.isNotEmpty(conditionStr) ? JsonUtil.toMap(conditionStr)
					: new HashMap<>();
			if (!map.isEmpty()) {
				if (map.containsKey("classifyId")) {
					map.put("classifyId", StringUtils.isEmpty(map.get("classifyId")) ? "" : map.get("classifyId")
							.toString());
				}
				if (map.containsKey("updateTime")) {
					map.put("updateTime", StringUtils.isEmpty(map.get("updateTime")) ? "" : map.get("updateTime")
							.toString());
				}
				if (map.containsKey("readNum")) {
					map.put("readNum", StringUtils.isEmpty(map.get("readNum")) ? "" : map.get("readNum").toString());
				}
				if (map.containsKey("name")) {
					map.put("name", StringUtils.isEmpty(map.get("name")) ? "" : "%" + map.get("name").toString() + "%");
				}
			}
			limit = limit == null ? 6 : limit;
			start = start != null && limit != null ? start : 0;
			map.put("limit", limit);
			map.put("start", start);
			list = spreadManagerService.querySpreadList(map);
		} catch (Exception e) {
			logger.error("getSpreadList  error:", e);
		}
		return list;
	}

	/**
	 * 根据id查询推广信息
	 * 
	 * @param id
	 * @return MarketSpreadManagerVo
	 */
	@RequestMapping("/getSpreadDetail")
	@ResponseBody
	public MarketSpreadManagerVo getSpreadDetailCon(String id, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		MarketSpreadManagerVo obj = null;
		try {
			obj = spreadManagerService.getSpreadDetailSerandAddReadNum(Long.valueOf(id));
		} catch (Exception e) {
			logger.error("根据id查询推广信息异常<getSpreadDetailCon>:", e);
		}
		return obj;
	}

	@RequestMapping("/selectSubbranchByPrimaryKey")
	@ResponseBody
	public SubbranchVo selectSubbranchByPrimaryKey(@RequestParam(value = "id", required = true) String id,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		SubbranchVo subbranchVo = null;
		if (!StringUtil.isEmpty(id)) {
			Long ids = Long.parseLong(id);

			try {
				subbranchVo = subbranchService.selectByPrimaryKey(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return subbranchVo;
	}

	/**
	 * 校验App登入信息
	 * 
	 * @param shopId
	 *            -店铺Id
	 * @return String
	 */
	@RequestMapping("/login/getAppShopLogin")
	public String checkAppLogin(@RequestParam(defaultValue = "") String shopId,
			@RequestParam(defaultValue = "") String userName, @RequestParam(defaultValue = "") String token,
			HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		if(StringUtils.isEmpty(shopId)){
			return "redirect:/www/index.html#/login";
		}else{
			return "redirect:/www/index.html#/home/"+shopId;
		}
	}
	
	@RequestMapping("getMoreColumnImg")
	@ResponseBody
	public Page getMoreColumnImg(@RequestParam(value = "limit", required = true) int limit,
			@RequestParam(value = "start", required = true) int start,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Page page=new Page();
		page.setLimit(limit);
		page.setStart(start);
		page=homePageImgService.selectMoreColumnImg(page);
		
		return page;
		
	}
}
