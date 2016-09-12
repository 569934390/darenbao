package com.compses.dao.base;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.UserRela;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public interface IUserRelaDao extends IBaseCommonDAO{

    UserRela getByUserId(String userId);

}
