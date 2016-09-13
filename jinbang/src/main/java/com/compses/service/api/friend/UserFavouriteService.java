package com.compses.service.api.friend;

import com.compses.dao.base.IUserRelaDao;
import com.compses.dao.friend.IUserFavouriteDao;
import com.compses.dto.Page;
import com.compses.model.MobileUserInfo;
import com.compses.model.UserFavourite;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.service.api.system.UserInfoService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
@Service
@Transactional
public class UserFavouriteService extends BaseCommonService{
    @Autowired
    private IUserFavouriteDao userFavouriteDao;
    @Autowired
    private MobileUserInfoService userInfoService;

    public UserFavourite addNewRelationship(UserFavourite userFavourite){
        userFavourite.setRelaId(UUIDUtils.getUUID());
        userFavouriteDao.saveForUUid(userFavourite);
        //修改收藏数
        MobileUserInfo mobileUserInfo = userInfoService.getById(userFavourite.getFavouriteUserId());
        if(null == mobileUserInfo.getFavouriteNum()){
            mobileUserInfo.setFavouriteNum(1);
        }else{
            mobileUserInfo.setFavouriteNum(mobileUserInfo.getFavouriteNum()+1);
        }
        RedisStringUtil.setRedis(RedisKeyConfig.USER_PREFIX+mobileUserInfo.getUserId(), StringUtils.convertObjectToMap(mobileUserInfo),false);
        return userFavourite;
    }

    public void deleteByUserId(String userId,String favouriteUserId){
        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setUserId(userId);
        userFavourite.setFavouriteUserId(favouriteUserId);
        userFavouriteDao.deleteByUserId(userFavourite);
        //修改收藏数
        MobileUserInfo mobileUserInfo = userInfoService.getById(userFavourite.getFavouriteUserId());
        mobileUserInfo.setFavouriteNum(mobileUserInfo.getFavouriteNum()-1);
        RedisStringUtil.setRedis(RedisKeyConfig.USER_PREFIX+mobileUserInfo.getUserId(), StringUtils.convertObjectToMap(mobileUserInfo),false);
    }

    public Page<MobileUserInfo> listPageForFavourite(String userId,Page<MobileUserInfo> page){
        Page<UserFavourite> userFavouritePage = new Page<UserFavourite>();
        userFavouritePage.setPage(page.getPage());
        userFavouritePage.setLimit(page.getLimit());
        userFavouritePage.setStart(page.getStart());
        userFavouritePage =  userFavouriteDao.listPageForFavourite(userId,userFavouritePage);
        List<MobileUserInfo> result = new ArrayList<MobileUserInfo>();
        for (UserFavourite userFavourite:userFavouritePage.getResult()){
//            String favouriteUserId = userFavourite.getUserId();
//            String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+favouriteUserId);
//            MobileUserInfo userInfo = JsonUtils.toBean(userJson,MobileUserInfo.class);
            MobileUserInfo userInfo = userInfoService.getById(userFavourite.getFavouriteUserId());
            result.add(userInfo);
        }
        page.setResult(result);
        return page;
    }

    public void addOrDelFavourite(String userId,String favouriteUserId){
        UserFavourite search = userFavouriteDao.listByConditions(userId,favouriteUserId);
        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setUserId(userId);
        userFavourite.setFavouriteUserId(favouriteUserId);
        if (search==null){
            this.addNewRelationship(userFavourite);
        }else {
            userFavouriteDao.delete(search);
        }
    }


}
