package com.club.web.weixin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.service.U8InterfaceService;
import com.club.web.deal.vo.IndentVo;
import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.domain.WeixinUserInfoDo;
import com.club.web.weixin.domain.repository.WeixinUserInfoRepository;
import com.club.web.weixin.service.ShanGuoWxpayService;
import com.club.web.weixin.util.WeiXinPayUtil;

@Service("shanGuoWxpayService")
public class ShanGuoWxpayServiceImpl implements ShanGuoWxpayService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public WeiXinPayUtil weiXinPayUtil;
	@Autowired
	public IndentService indentService;
	@Autowired
	public U8InterfaceService u8InterfaceService;
	@Autowired
	public WeixinUserInfoRepository weixinUserInfoRepository;

	/**
	 * 支付
	 */
	@Override
	public Map<String, Object> getWxPayInfo(Long orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String out_trade_no = orderId + "";
		double moneys = 1; // 总金额
		String attach = "";
		// 接收财付通通知的URL
		String notify_url = WeixinGlobal.getWeixinUrl() + "/weixin/weixinPay/wxPayCallBack.do";
		IndentVo indentVo = indentService.findVoById(orderId);
		moneys = Double.parseDouble(indentVo.getPaymentAmount() == null ? "0" : indentVo.getPaymentAmount());
		String body = indentVo.getIndentList().size()>1?indentVo.getIndentList().get(0).getTradeGoodName()+"等":indentVo.getIndentList().get(0).getTradeGoodName();

		// 获取openId
		WeixinUserInfoDo eventActivityTypeDo = weixinUserInfoRepository.findWeixinUserInfoDoById(indentVo.getBuyerId());
		String openid = eventActivityTypeDo.getOpenid();
		
		return weiXinPayUtil.getWxPayInfo(openid, out_trade_no, moneys, body, attach, notify_url, request, response);
	}

	/**
	 * 微信退款
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> WxRePay(Long orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resInfo = new HashMap<String, Object>();
		String out_trade_no = orderId + "";
		IndentVo indentVo = indentService.findVoById(orderId);
		double total_fee = Double.parseDouble(indentVo.getPaymentAmount() == null ? "0" : indentVo.getPaymentAmount());
		double refund_fee = total_fee;
		Map<String, Object> result = weiXinPayUtil.WxRePay(out_trade_no, out_trade_no, total_fee, refund_fee, request,
				response);
		if ((boolean) result.get("code")) {
			// 获取openId
			WeixinUserInfoDo eventActivityTypeDo = weixinUserInfoRepository
					.findWeixinUserInfoDoById(indentVo.getBuyerId());
			String openid = eventActivityTypeDo.getOpenid();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("payType", 1);
			map.put("payAccount", openid);
			map.put("paymentAmount", StringUtils.isEmpty(total_fee) ? "0" : total_fee);
			map.put("payTime", new Date());
			indentService.updateStatus(out_trade_no, "refund", new HashMap<String, Object>(), map);
			resInfo.put("code", true);
			resInfo.put("msg", "退款成功！");
		}else{
			resInfo.put("code", false);
			resInfo.put("msg", "退款失败！");
		}
		return resInfo;

	}

	/**
	 * 付款回调
	 */
	public void payCallBack(Map<String, String> requestMap) throws Exception {

		logger.info("回调情况  requestMap:{}", JsonUtil.toJson(requestMap));

		String out_trade_no = requestMap.get("out_trade_no");
		String result_code = requestMap.get("result_code");
		String total_fee = (Double.parseDouble(requestMap.get("total_fee")) / 100) + "";
		String openid = requestMap.get("openid"); // 用户标识

		// 如果支付成功，则更新状态
		if (result_code.equals("SUCCESS")) {
			IndentVo indentVo=indentService.findVoForWeixinCallBackById(Long.parseLong(out_trade_no));
			if(indentVo!=null&&indentVo.getStatus()==1){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("payType", 1);
				map.put("payAccount", openid);
				map.put("paymentAmount", StringUtils.isEmpty(total_fee) ? "0" : total_fee);
				map.put("payTime", new Date());
				indentService.updateStatus(out_trade_no, "ship", new HashMap<String, Object>(), map);
				u8InterfaceService.account(out_trade_no);
				logger.error("shanGuoWxpayService payCallBack msg : {}" ,out_trade_no+"：状态更新成功！");
			}else{
				logger.error("shanGuoWxpayService payCallBack msg : {}" ,"该订单的状态已经更新过！");
			}
		}
	}

	@Override
	public Map<String, Object> wxCloseOrder(Long orderId, HttpServletRequest request, HttpServletResponse response) {
		return weiXinPayUtil.wxCloseOrder(orderId+"", request, response);
	}
}