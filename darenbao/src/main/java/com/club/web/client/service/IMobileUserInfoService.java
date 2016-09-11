package com.club.web.client.service;

import com.club.framework.exception.BaseAppException;
import com.club.web.client.vo.MobileUserInfoVo;
import com.club.web.common.service.IBaseDoService;
import com.club.web.common.service.IBaseService;

/**
 * Created by Administrator on 2016/9/11.
 */
public interface IMobileUserInfoService extends IBaseDoService {

    MobileUserInfoVo saveOpUpdate(MobileUserInfoVo mobileUserInfo) throws BaseAppException;

    MobileUserInfoVo getByInvitationCode(String invitationCode) throws BaseAppException;
}
