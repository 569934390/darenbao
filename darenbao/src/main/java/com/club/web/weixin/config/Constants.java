package com.club.web.weixin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    private static Logger logger = LoggerFactory.getLogger(Constants.class);

    public static final String WEIXIN_USERINFO_SESSION = "weixinUserInfoVo"; //微信用户SESSION标签
    
    public static final String EVENT_ACTIVITY_USERINFO_SESSION = "eventActivityUserinfoVo"; //微信活动用户SESSION标签
}
