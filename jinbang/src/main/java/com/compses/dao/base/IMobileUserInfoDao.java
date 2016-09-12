package com.compses.dao.base;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.MobileUserInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public interface IMobileUserInfoDao extends IBaseCommonDAO{

    public MobileUserInfo getByUserId(String userId);

    public MobileUserInfo getByMobile(MobileUserInfo mobileUserInfo);

    MobileUserInfo getByInvitationCode(String invitationCode);

    MobileUserInfo getByParentUserId(String parentUserId);

    List<MobileUserInfo> getSubordinate(String userId);

    MobileUserInfo getParent(String userId);

    public Page<MobileUserInfo> getFirstSub(MobileUserInfo mobileUserInfo ,Page<MobileUserInfo> page);

    Page<MobileUserInfo> getSecondSub(MobileUserInfo mobileUserInfo ,Page<MobileUserInfo> page);

    Page<MobileUserInfo> getThridSub(MobileUserInfo mobileUserInfo ,Page<MobileUserInfo> page);

}
