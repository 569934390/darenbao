package com.compses.dao.base;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.UserCardPwd;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public interface IUserCardPwdDao extends IBaseCommonDAO{

    UserCardPwd getByUserId(String userId);

    UserCardPwd getByMobile(String mobile);
}
