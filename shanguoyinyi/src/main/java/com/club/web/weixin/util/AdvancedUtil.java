package com.club.web.weixin.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.club.web.weixin.vo.WeixinUserInfoVo;
import com.club.web.weixin.weixinpojo.AccessToken;
import com.club.web.weixin.weixinpojo.JsapiTicket;
import com.club.web.weixin.weixinpojo.WeixinOauth2Token;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @Title: AdvancedUtil.java
 * @Package com.club.web.client.util
 * @Description: TODO(微信网络接口)
 * @author 柳伟军
 * @date 2016年4月16日 下午2:55:55
 * @version V1.0
 */
public class AdvancedUtil {

	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	// 获取jsapi_ticket的接口地址（GET） 限200（次/天）
	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	/**
	 * 获取jsapi_ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public static JsapiTicket getJsapiTicket(AccessToken accessToken) {
		JsapiTicket jsapiTicket = null;
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accessToken.getToken());
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				jsapiTicket = new JsapiTicket();
				jsapiTicket.setTicket(jsonObject.getString("ticket"));
				jsapiTicket.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				jsapiTicket = null;
				// 获取token失败
				log.error("获取jsapiTicket失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return jsapiTicket;
	}

	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId
	 *            公众账号的唯一标识
	 * @param appSecret
	 *            公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	/**
	 * 刷新网页授权凭证
	 * 
	 * @param appId
	 *            公众账号的唯一标识
	 * @param refreshToken
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken
	 *            网页授权接口调用凭证
	 * @param openId
	 *            用户标识
	 * @return SNSUserInfo
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static WeixinUserInfoVo getWeixinUserInfoVo(String accessToken, String openId) {
		WeixinUserInfoVo weixinUserInfoVo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfoVo = new WeixinUserInfoVo();
				// 用户的标识
				weixinUserInfoVo.setOpenid(jsonObject.getString("openid"));
				// 昵称
				weixinUserInfoVo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				weixinUserInfoVo.setSex(jsonObject.getLong("sex"));
				// 用户所在国家
				weixinUserInfoVo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfoVo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfoVo.setCity(jsonObject.getString("city"));
				// 用户头像
				weixinUserInfoVo.setHeadimgurl(jsonObject.getString("headimgurl"));

			} catch (Exception e) {
				weixinUserInfoVo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinUserInfoVo;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfoVo
	 */
	public static WeixinUserInfoVo getUserInfo(String accessToken, String openId) {
		WeixinUserInfoVo weixinUserInfoVo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfoVo = new WeixinUserInfoVo();
				// 用户的标识
				weixinUserInfoVo.setOpenid(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfoVo.setSubscribe(jsonObject.getLong("subscribe"));
				// 用户关注时间
				weixinUserInfoVo.setSubscribetime(new Date(jsonObject.getLong("subscribe_time") * 1000));
				// 昵称
				weixinUserInfoVo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfoVo.setSex(jsonObject.getLong("sex"));
				// 用户所在国家
				weixinUserInfoVo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfoVo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfoVo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfoVo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfoVo.setHeadimgurl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == weixinUserInfoVo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfoVo.getOpenid());
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfoVo;
	}

}
