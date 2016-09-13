package com.compses.service.api.service;

import com.compses.dao.friend.IFriendRelaDao;
import com.compses.dto.FriendRelaDTO;
import com.compses.model.FriendRela;
import com.compses.model.MobileUserInfo;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */

@Service
@Transactional
public class FriendRelaService extends BaseCommonService{

    @Autowired
    private IFriendRelaDao friendRelaDao;

    @Autowired
    private MobileUserInfoService mobileUserInfoService;


    public int add(FriendRela friendRela)throws Exception{
        switch (Integer.parseInt(friendRela.getStatus())){
            case 1:
                List<FriendRela> friendRelaList  = loadData(friendRela);
                if (friendRelaList.size()==0){
                    friendRela.setFriendId(UUIDUtils.getUUID());
                    friendRelaDao.saveForUUid(friendRela);
                }else {
                    return 0;
                }
            return 1;
//            break;
            case -1:
                FriendRela searchRela  = new FriendRela();
                PropertyUtils.copyProperties(searchRela,friendRela);
                searchRela.setStatus(null);
                friendRelaDao.deleteByUserConditions(searchRela);
                friendRela.setFriendId(UUIDUtils.getUUID());
                friendRelaDao.saveForUUid(friendRela);
                return 1;
//            break;
        }
        return 1;
    }

    public void del(FriendRela friendRela)throws Exception{
        friendRelaDao.deleteByUserConditions(friendRela);
    }

    public List<FriendRelaDTO> listFriend(String userId,String status)throws Exception{
        List<FriendRelaDTO> res = friendRelaDao.listFriend(userId,status);
        return res;
    }

    public List<FriendRelaDTO> selectListByConditions(String condition)throws Exception{
        List<FriendRelaDTO> res = friendRelaDao.selectListByConditions(condition);
        return res;
    }

    public List<FriendRelaDTO> findUserByConditions(String condition){
        return friendRelaDao.findUserByConditions(condition);
    }
}
