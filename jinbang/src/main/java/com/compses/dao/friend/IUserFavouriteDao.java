package com.compses.dao.friend;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.UserFavourite;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public interface IUserFavouriteDao extends IBaseCommonDAO{

    void deleteByUserId(UserFavourite userFavourite);

    Page<UserFavourite> listPageForFavourite(String userId,Page<UserFavourite> page);

    UserFavourite listByConditions(String userId,String favouriteUserId);
}
