package com.club.web.weixin.config;

import org.apache.log4j.Logger;

public class Constants {
	
	public static Logger logger = Logger.getLogger(Constants.class);

    public static final String WEIXIN_USERINFO_SESSION = "weixinUserInfoVo"; //微信用户SESSION标签
    
    public static final String EVENT_ACTIVITY_USERINFO_SESSION = "eventActivityUserinfoVo"; //微信活动用户SESSION标签
}
