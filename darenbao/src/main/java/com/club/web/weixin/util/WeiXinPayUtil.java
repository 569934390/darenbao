package com.club.web.weixin.util;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.club.framework.util.JsonUtil;

/**
 * @Title: WeiXinPayUtil.java
 * @Package com.club.web.weixin.util
 * @Description: TODO(微信支付)
 * @author 柳伟军
 * @date 2016年5月11日 上午9:23:40
 * @version V1.0
 */
@Component
@PropertySource("classpath:/config/weixin.properties")
public class WeiXinPayUtil {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${appid}")
	private String appId;
	@Value("${appsecret}")
	private String appsecret;
	@Value("${partner}")
	private String partner;
	@Value("${partner}")
	private String mch_id;
	@Value("${partnerkey}")
	private String partnerkey;

	/**
	 * 微信支付
	 * 
	 * @param openid
	 *            用户唯一标识
	 * @param orderId
	 *            订单号
	 * @param moneys
	 *            支付金额（保留两位小数）
	 * @param body
	 *            支付内容
	 * @param attach
	 *            支付附加内容
	 * @param notify_url
	 *            回调函数
	 * @param request
	 * @param response
	 * @return
	 */
	public SortedMap<String, Object> getWxPayInfo(String openid, String out_trade_no, double moneys, String body,
			String attach, String notify_url, HttpServletRequest request, HttpServletResponse response) {

		SortedMap<String, Object> resInfo = new TreeMap<String, Object>();
		// 用户IP
		String ip = CusAccessObjectUtil.getIpAddress(request);
		String trade_type = "JSAPI";// 支付类型

		Date now = new Date();
		Date afterDate = new Date(new Date().getTime() + 300 * 1000);

		String finalmoney = String.format("%.2f", moneys);
		String money = Long.parseLong(finalmoney.replace(".", "")) + "";

		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		// 随机数
		String nonce_str = strReq;

		// 接收财付通通知的URL
		SortedMap<String, String> packageParams = new TreeMap<String, String>();

		// 设置获取prepayid支付参数
		packageParams.put("appid", appId);
		packageParams.put("mch_id", mch_id);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("total_fee", money);// money
		packageParams.put("spbill_create_ip", ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openid);

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appId, appsecret, partnerkey);

		// 签名
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" //
				+ "<appid>" + appId + "</appid>" //
				+ "<mch_id>" + mch_id + "</mch_id>" //
				+ "<nonce_str>" + nonce_str + "</nonce_str>" //
				+ "<sign>" + sign + "</sign>" //
				+ "<body><![CDATA[" + body + "]]></body>"//
				+ "<attach>" + attach + "</attach>" //
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"//
				+ "<total_fee>" + money + "</total_fee>" //
				+ "<spbill_create_ip>" + ip + "</spbill_create_ip>"//
				+ "<notify_url>" + notify_url + "</notify_url>" //
				+ "<trade_type>" + trade_type + "</trade_type>"//
				+ "<openid>" + openid + "</openid>" //
				+ "</xml>";
		logger.info("微信支付签名组装XML ： " + xml);
		String allParameters = "";
		try {
			allParameters = reqHandler.genPackage(packageParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 支付接口
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			Map<String, Object> dataMap = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if ((boolean) dataMap.get("code") == false) {
				resInfo.put("code", dataMap.get("code"));
				resInfo.put("msg", dataMap.get("msg"));
				return resInfo;
			} else {
				prepay_id = (String) ((Map<String, Object>) dataMap.get("msg")).get("prepay_id");
			}
		} catch (Exception e1) {
			resInfo.put("code", false);
			resInfo.put("msg", "签名失败!");
			logger.info("微信支付签名失败 ：error  {} " + e1.getMessage());
			e1.printStackTrace();
		}

		SortedMap<String, String> returnParam = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String prepay_id2 = "prepay_id=" + prepay_id;
		returnParam.put("appId", appId);
		returnParam.put("timeStamp", timestamp);
		returnParam.put("nonceStr", nonce_str);
		returnParam.put("package", prepay_id2);
		returnParam.put("signType", "MD5");
		String finalsign = reqHandler.createSign(returnParam);
		returnParam.put("sign", finalsign);
		resInfo.put("code", true);
		resInfo.put("msg", returnParam);
		return resInfo;
	}

	/**
	 * 微信支付退款
	 * 
	 * @param out_refund_no
	 *            退款单号
	 * @param out_trade_no
	 *            订单号
	 * @param total_fee
	 *            总金额 (保留两位小数)
	 * @param refund_fee
	 *            退款金额 (保留两位小数)
	 * @param request
	 * @param response
	 * @return
	 */
	public SortedMap<String, Object> WxRePay(String out_refund_no, String out_trade_no, double total_fee,
			double refund_fee, HttpServletRequest request, HttpServletResponse response) {

		SortedMap<String, Object> resInfo = new TreeMap<String, Object>();

		// 总金额
		String finalTotalmoney = String.format("%.2f", total_fee);
		String total = Long.parseLong(finalTotalmoney.replace(".", "")) + "";
		// 退款金额
		String finalRefundmoney = String.format("%.2f", refund_fee);
		String refund = Long.parseLong(finalRefundmoney.replace(".", "")) + "";

		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		// 随机数
		String nonce_str = strReq;
		String op_user_id = mch_id;// 就是MCHID
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total);
		packageParams.put("refund_fee", refund);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appId, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" //
				+ "<appid>" + appId + "</appid>" //
				+ "<mch_id>" + mch_id + "</mch_id>" //
				+ "<nonce_str>" + nonce_str + "</nonce_str>" //
				+ "<sign><![CDATA[" + sign + "]]></sign>"//
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>" //
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"//
				+ "<total_fee>" + total + "</total_fee>"//
				+ "<refund_fee>" + refund + "</refund_fee>"//
				+ "<op_user_id>" + op_user_id + "</op_user_id>" //
				+ "</xml>";

		// 退款接口
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			Map map = ClientCustomSSL.doRefund(mch_id, createOrderURL, xml);
			logger.info("微信退款回复 ： " + JsonUtil.toJson(map));
			resInfo.putAll(map);
		} catch (Exception e) {
			resInfo.put("code", false);
			resInfo.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return resInfo;
	}

	/**
	 * 取消订单
	 * @param out_trade_no
	 * @param request
	 * @param response
	 * @return
	 */
	public SortedMap<String, Object> wxCloseOrder(String out_trade_no, HttpServletRequest request,
			HttpServletResponse response) {

		SortedMap<String, Object> resInfo = new TreeMap<String, Object>();

		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		// 随机数
		String nonce_str = strReq;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", mch_id);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("nonce_str", nonce_str);

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appId, appsecret, partnerkey);

		// 签名
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" //
				+ "<appid>" + appId + "</appid>" //
				+ "<mch_id>" + mch_id + "</mch_id>" //
				+ "<nonce_str>" + nonce_str + "</nonce_str>" //
				+ "<sign>" + sign + "</sign>" //
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"//
				+ "</xml>";
		logger.info("微信取消订单签名组装XML ： " + xml);

		// 取消订单接口
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/closeorder";
		try {
			Map<String, Object> dataMap = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if ((boolean) dataMap.get("code") == false) {
				resInfo.put("code", dataMap.get("code"));
				resInfo.put("msg", dataMap.get("msg"));
				return resInfo;
			} else {
				resInfo.put("code", true);
				resInfo.put("msg", dataMap);
			}
		} catch (Exception e1) {
			resInfo.put("code", false);
			resInfo.put("msg", "签名失败!");
			logger.info("微信取消订单失败 ：error  {} " + e1.getMessage());
			e1.printStackTrace();
		}
		return resInfo;
	}
}
