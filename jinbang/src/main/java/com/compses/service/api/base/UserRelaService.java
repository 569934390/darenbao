package com.compses.service.api.base;

import com.compses.dao.base.IUserRelaDao;
import com.compses.framework.ResultData;
import com.compses.model.UserRela;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

@Service
@Transactional
public class UserRelaService extends BaseCommonService {

    @Autowired
    private IUserRelaDao userRelaDao;


    public UserRela addNewRela(UserRela userRela){
        userRela.setRelaId(UUIDUtils.getUUID());
        userRelaDao.saveForUUid(userRela);
        RedisStringUtil.setRedis(RedisKeyConfig.USER_RELA_PREFIX+userRela.getUserId(), StringUtils.convertObjectToMap(userRela),false);
        return userRela;
    }

    public UserRela getByUserId(String userId){
        String relaJson  = RedisStringUtil.get(RedisKeyConfig.USER_RELA_PREFIX+userId);
        UserRela rela = new UserRela();
        if (StringUtils.isEmpty(relaJson)){
            rela = userRelaDao.getByUserId(userId);
        }else {
            rela = JsonUtils.toBean(relaJson,UserRela.class);
        }
        return rela;
    }

}
