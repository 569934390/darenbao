package com.club.web.client.controller;

import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.ExceptionHandler;
import com.club.framework.exception.SystemErrorCode;
import com.club.framework.util.StringUtils;
import com.club.web.client.domain.MobileUserInfoDo;
import com.club.web.client.service.impl.MobileUserInfoServiceImpl;
import com.club.web.client.vo.MobileUserInfoVo;
import com.club.web.common.PhoneCodeCache;
import com.club.web.common.vo.BaseVo;
import com.club.web.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
* Created by Administrator on 2016/9/11.
*/
@RequestMapping("client")
@Controller
public class LoginController {
    @Autowired
    private MobileUserInfoServiceImpl mobileUserInfoService;
    @Autowired
    SmsUtil smsUtil;
    /**
     * 注册账号验证
     * @param phoneCode
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/mobile/register")
    @ResponseBody
    private MobileUserInfoVo register(String phoneCode,String verifyCode,String invitationCode) throws BaseAppException {
        try{
            if(!StringUtils.isEmpty(phoneCode)){
                String verifyCodeSend = PhoneCodeCache.getVerifyCode(phoneCode);
                if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
//					if (1==1){
//					phoneCodeCache.remove(phoneCode);
                    MobileUserInfoVo mobileUserInfo = new MobileUserInfoVo();
                    if (!StringUtils.isEmpty(invitationCode)){
                        MobileUserInfoVo searchUserInfo = mobileUserInfoService.getByInvitationCode(invitationCode);
//                        AgentInfo agentInfo = agentInfoService.getByMobile(invitationCode);
                        if (searchUserInfo ==null){
                            ExceptionHandler.publish(SystemErrorCode.CUSTOM_EXCEPTION,"邀请码无效！");
                        }
                        if (mobileUserInfo!=null){
                            mobileUserInfo.setParentUserId(searchUserInfo.getUserId());
                        }
                    }
                    mobileUserInfo.setUserId(null);
                    mobileUserInfo.setMobile(phoneCode);
                    mobileUserInfo = mobileUserInfoService.saveOpUpdate(mobileUserInfo);
                    return mobileUserInfo;
                }else{
                    ExceptionHandler.publish(SystemErrorCode.CUSTOM_EXCEPTION,"验证失败！");
                }
            }else{
                ExceptionHandler.publish(SystemErrorCode.CUSTOM_EXCEPTION,"您输入的手机号码为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            ExceptionHandler.publish(SystemErrorCode.UNKNOWN_RUNTIME_EXCEPTION,e);
        }
        return null;
    }

    /**
     * 根据手机号获取服务端验证码
     * @param phoneCode
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    @RequestMapping(value = "/mobile/verifyPhone.do" )
    @ResponseBody
    public BaseVo verifyPhone(String phoneCode) throws BaseAppException {
        try {
            if(!StringUtils.isEmpty(phoneCode)){
                String verifyCode="";
                if (PhoneCodeCache.containPhoneCode(phoneCode)){
                    verifyCode = PhoneCodeCache.getVerifyCode(phoneCode);
                    PhoneCodeCache.removeVerifyCode(verifyCode);
                }else{
                    verifyCode = this.getVerifyCode(phoneCode);
                }
                smsUtil.sendRegistVerifyCode(phoneCode,verifyCode);
                return new BaseVo(true,"短信已发送!");
            }else {
                ExceptionHandler.publishMsg("您输入的手机号码为空!");
            }
        }catch (Exception e){
           ExceptionHandler.publishMsg("短信发送失败!");
        }
        return null;
    }


    private String getVerifyCode(String phoneCode){
        return PhoneCodeCache.getVerifyCode(phoneCode);
    }

    /**
     * 验证码验证
     * @param phoneCode
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/mobile/verifyPhoneCode.do")
    @ResponseBody
    private BaseVo verifyPhoneCode(String phoneCode,String verifyCode) throws BaseAppException {
        try{
            if(!StringUtils.isEmpty(phoneCode)){
                //测试代码
//                if (verifyCode.length()==4){
//                    MobileUserInfoDo mobileUserInfoDo=new MobileUserInfoDo();
//                    mobileUserInfoDo.setMobile(phoneCode);
//                    MobileUserInfoVo mobileUserInfo=mobileUserInfoDo.selectOne();
//                    if (mobileUserInfo==null){
//                        ExceptionHandler.publishMsg("该用户不存在!");
//                    }
//                    return mobileUserInfo;
//                }
                //
                String verifyCodeSend = getVerifyCode(phoneCode);
                if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
                    MobileUserInfoDo mobileUserInfoDo=new MobileUserInfoDo();
                    mobileUserInfoDo.setMobile(phoneCode);
                    MobileUserInfoVo mobileUserInfo=mobileUserInfoDo.selectOne();
                    if (mobileUserInfo==null){
                        ExceptionHandler.publishMsg("您输入的手机号码未注册!");
                    }
                    PhoneCodeCache.removeVerifyCode(phoneCode);
                    return mobileUserInfo;
                }else{
                    ExceptionHandler.publishMsg("验证失败！");
                }
            }else{
                ExceptionHandler.publishMsg("您输入的手机号码为空！");
            }
        }catch (Exception e){
            ExceptionHandler.publishMsg("服务器异常！",e);
        }
        return null;
    }

}
