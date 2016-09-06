package com.club.web.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.club.framework.util.HttpUtils;
import com.club.framework.util.StringUtils;
import com.club.web.store.vo.PushMsgVo;

@Component
@PropertySource("classpath:/config/sms.properties")
public class SmsUtil {

	private Logger logger = LoggerFactory.getLogger(SmsUtil.class);

	@Value("${sms.address}")
	private String address;
	@Value("${sms.uri}")
	private String uri;
	@Value("${sms.userid}")
	private String userid;
	@Value("${sms.token}")
	private String token;

	@Value("${sms.template.regist}")
	private String registTemplate;
	@Value("${sms.template.delivery}")
	private String deliveryTemplate;
	
	@Value("${sms.template.buymyself}")
	private String buymyselfTemplate;
	@Value("${sms.template.buyother}")
	private String buyotherTemplate;
	@Value("${sms.template.coupon}")
	private String couponTemplate;

	@Value("${sms.appKey}")
	private String appKey;
	@Value("${sms.secretKey}")
	private String secretKey;
	@Value("${sms.hostPush}")
	private String hostPush;
	@Value("${sms.hostPushStatus}")
	private String hostPushStatus;
	@Value("${sms.channel}")
	private String channel;

	private String send(String phone, String msg) {
		try {
			String message = URLEncoder.encode(msg, "utf-8");
			String query = uri + "?userId=" + userid + "&mobile=" + phone + "&content=" + msg;
			String sign = StringUtils.md5(query + token);
			String result = HttpUtils.sendGet(address + uri, "userId=" + userid + "&mobile=" + phone + "&content="
					+ message + "&sign=" + sign);
			logger.debug("发送短信: {}, 地址: {}, 结果: {}.", msg, address, result);
			return result;
		} catch (Exception e) {
			logger.debug("短信发送失败");
			return null;
		}
	}

	public String sendRegistVerifyCode(String phone, String verifyCode) {
		return send(phone, registTemplate.replace("{verifyCode}", verifyCode));
	}

	public String sendDeliveryInfo(String phone, String username, String orderno, String deliveryname, String deliveryno) {
		return send(
				phone,
				deliveryTemplate.replace("{username}", username).replace("{orderno}", orderno)
						.replace("{deliveryname}", deliveryname).replace("{deliveryno}", deliveryno));
	}

	/**
	 * 订单发货时，买给自己商品的消息
	 * @return
	 */
	public String sendBuyMyselInfo(String phone, String shopName, String buyer, String indentName, String expressCompany, String expressNumber) {
		return send(
				phone,
				buymyselfTemplate.replace("{shopName}", shopName).replace("{buyer}", buyer).replace("{indentName}", indentName)
				.replace("{expressCompany}", expressCompany).replace("{expressNumber}", expressNumber));
	}
	
	/**
	 * 订单发货时，买给朋友商品的消息
	 * @return
	 */
	public String sendBuyOtherInfo(String phone, String shopName, String receiver, String expressCompany, String expressNumber) {
		return send(
				phone,
				buyotherTemplate.replace("{shopName}", shopName).replace("{receiver}", receiver)
						.replace("{expressCompany}", expressCompany).replace("{expressNumber}", expressNumber));
	}
	
	/**
	 * 订单发货时，卡券兑换商品的消息
	 * @return
	 */
	public String sendCouponInfo(String phone, String ticketNum, String receiver, String expressCompany, String expressNumber) {
		return send(
				phone,
				couponTemplate.replace("{ticketNum}", ticketNum).replace("{receiver}", receiver)
						.replace("{expressCompany}", expressCompany).replace("{expressNumber}", expressNumber));
	}
	
	/**
	 * 消息推送
	 * 
	 * 
	 * @param pushObj
	 * @param customerOrder
	 * @add by 2016-03-23
	 */
	public Map<String, Object> pushMsgUtil(PushMsgVo pushObj, String customerOrder) throws Exception {

		pushObj.setCustomerOrder(customerOrder);
		pushObj.setAppKey(appKey);
		pushObj.setSecretKey(secretKey);
		pushObj.setUserId(Long.valueOf(userid));
		String sign = CommonUtil.md5("POST" + address + hostPush + pushObj.getUserId() + pushObj.getAppKey()
				+ pushObj.getCustomerOrder() + token);
		pushObj.setSign(sign);
		pushObj.setChannel(channel);
		Map<String, Object> result = httpPost(address + hostPush, JSONObject.fromObject(pushObj));
		return result;
	}

	/**
	 * 查看消息推送结果
	 * 
	 * 
	 * @param pushObj
	 * @param customerOrder
	 * @add by 2016-03-23
	 */
	public Map<String, Object> getMsgPushStatus(PushMsgVo pushObj) throws Exception {
		pushObj.setAppKey(appKey);
		pushObj.setSecretKey(secretKey);
		pushObj.setUserId(Long.valueOf(userid));
		String sign = CommonUtil.md5("POST" + address + hostPushStatus + pushObj.getUserId() + pushObj.getAppKey()
				+ token);
		pushObj.setSign(sign);
		pushObj.setChannel(channel);
		Map<String, Object> result = httpPost(address + hostPushStatus, JSONObject.fromObject(pushObj));
		return result;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> httpPost(String url, JSONObject param) throws Exception {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse httpResponse = null;
		JSONObject resultJson = null;
		Map<String, Object> result = null;
		try {
			HttpPost httpRequest = new HttpPost(url);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			formParams.add(new BasicNameValuePair("param", param.toString()));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
			httpRequest.setEntity(formEntity);
			httpclient = HttpClients.createDefault();
			httpResponse = httpclient.execute(httpRequest);

			HttpEntity resultEntity = httpResponse.getEntity();
			String strResult = EntityUtils.toString(resultEntity);
			resultJson = JSONObject.fromObject(strResult);

			if (resultJson != null) {
				result = (Map<String, Object>) JSONObject.toBean(resultJson, Map.class);
			}
		} finally {
			if (httpResponse != null)
				try {
					httpResponse.close();
				} catch (IOException e1) {
				}
			if (httpclient != null)
				try {
					httpclient.close();
				} catch (IOException e) {
				}
		}
		return result;
	}

}
