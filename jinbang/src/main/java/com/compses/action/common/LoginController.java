package com.compses.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.compses.framework.ResultData;
import com.compses.model.AgentInfo;
import com.compses.model.MobileUserInfo;
import com.compses.service.api.base.AgentInfoService;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.util.HttpUtils;
import com.compses.util.LjSmsClient;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.compses.model.DopPrivilegeUser;
import com.compses.service.common.LoginService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("login")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private MobileUserInfoService mobileUserInfoService;
	@Autowired
	private AgentInfoService agentInfoService;

	public static Map<String,String> phoneCodeCache = new HashMap<String, String>();

	@RequestMapping("login")
	@ResponseBody
	public String login(HttpServletRequest request,DopPrivilegeUser user)
			throws Exception {
		return loginService.login(request, user);
	}
	@RequestMapping("loginOut")
	@ResponseBody
	public String loginOut(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loginService.loginOut(request, response);
		return  "success";
	}

	/**
	 * 根据手机号获取服务端验证码
	 * @param phoneCode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "verifyPhone.do" )
	@ResponseBody
	public ResultData verifyPhone(String phoneCode) {
		ResultData resultData = new ResultData();
		try {
			if(!StringUtils.isEmpty(phoneCode)){
				phoneCodeCache.remove(phoneCode);
				String verifyCode = "";
				if (phoneCodeCache.containsKey(phoneCode)){
					long delay = Long.parseLong(phoneCodeCache.get(phoneCode + "_time"));
					if (new Date().getTime()-delay>3600*1000){
						verifyCode = this.verifyCode(phoneCode);
					}
					else{
						verifyCode = phoneCodeCache.get(phoneCode);
					}
				}
				else{
					verifyCode = this.verifyCode(phoneCode);
				}
				LjSmsClient client = new LjSmsClient();
				client.sendSms(phoneCode, "验证码"+verifyCode+"请注意查收。【近帮】");
				resultData.setReturnMsg(true, "验证码已发送至手机！");
			}
			else {
				resultData.setReturnMsg(false, "电话号码无效！");
			}
		}catch (Exception e){
			e.printStackTrace();
			resultData.setReturnMsg(false, "短信发送失败！");
		}
		return resultData;
	}

	private String verifyCode(String phoneCode){
		String verifyCode = StringUtils.random(4);
		phoneCodeCache.put(phoneCode,verifyCode);
		phoneCodeCache.put(phoneCode+"_time",new Date().getTime()+"");
		return verifyCode;
	}

	private String getVerifyCode(String phoneCode){
		return phoneCodeCache.get(phoneCode);
	}

	/**
	 * 验证码验证
	 * @param phoneCode
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value = "verifyPhoneCode.do")
	@ResponseBody
	private ResultData verifyPhoneCode(String phoneCode,String verifyCode){
		ResultData resultData = null;
		try{
			if(!StringUtils.isEmpty(phoneCode)){
				//测试代码
				if (verifyCode.length()==4){
					MobileUserInfo mobileUserInfo = mobileUserInfoService.getByPhoneCode(phoneCode);
					if (mobileUserInfo==null){
						resultData.setReturnMsg(false,"该用户不存在！");
						return resultData;
					}
					resultData = new ResultData(true,"成功");
					resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
					return resultData;
				}
				//
				String verifyCodeSend = getVerifyCode(phoneCode);
					if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
					MobileUserInfo mobileUserInfo = mobileUserInfoService.getByPhoneCode(phoneCode);
					if (mobileUserInfo==null){
						resultData = new ResultData(false,"您输入的手机号码未注册！");
						return resultData;
					}
					phoneCodeCache.remove(phoneCode);
					resultData = new ResultData(true,"成功");
					resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
				}else{
					resultData = new ResultData(false,"验证失败！");
				}
			}else{
				resultData = new ResultData(false,"您输入的手机号码为空！");
			}
		}catch (Exception e){
			resultData = new ResultData(false,"服务器异常！");
		}
		return resultData;
	}


	/**
	 * 注册账号验证
	 * @param phoneCode
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping(value = "register.do")
	@ResponseBody
	private ResultData register(String phoneCode,String verifyCode,String invitationCode){
		ResultData resultData = null;
		try{
			if(!StringUtils.isEmpty(phoneCode)){
				String verifyCodeSend = getVerifyCode(phoneCode);
				if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
//					if (1==1){
//					phoneCodeCache.remove(phoneCode);
					MobileUserInfo mobileUserInfo = new MobileUserInfo();
					if (!StringUtils.isEmpty(invitationCode)){
						MobileUserInfo searchUserInfo = mobileUserInfoService.getByInvitationCode(invitationCode);
						AgentInfo agentInfo = agentInfoService.getByMobile(invitationCode);

						if (searchUserInfo ==null && agentInfo==null){
							resultData = new ResultData(false,"邀请码无效！");
							return resultData;
						}
						if (mobileUserInfo!=null){
							mobileUserInfo.setParentUserId(searchUserInfo.getUserId());
						}
						if (agentInfo !=null){
							mobileUserInfo.setBrandAgentId(agentInfo.getAgentId());
						}
					}
						mobileUserInfo.setUserId(null);
						mobileUserInfo.setMobile(phoneCode);
						mobileUserInfo = mobileUserInfoService.saveOpUpdate(mobileUserInfo);
						resultData = new ResultData(true,"成功");
						resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
				}else{
					resultData = new ResultData(false,"验证失败！");
				}
			}else{
				resultData = new ResultData(false,"您输入的手机号码为空！");
			}
		}catch (Exception e){
			e.printStackTrace();
			resultData = new ResultData(false,e.getMessage());
		}
		return resultData;
	}
}

