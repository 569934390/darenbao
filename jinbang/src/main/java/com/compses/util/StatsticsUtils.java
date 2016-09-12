package com.compses.util;

import com.compses.model.*;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class StatsticsUtils {

    //用户订单计算
    public static void statsticsUserOrder(String userId,String rebateRule){
        String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+userId);
        MobileUserInfo mobileUserInfo = JsonUtils.toBean(userJson,MobileUserInfo.class);

    }
    //运营商订单计算
    public static void statsticsAgentOrder(String agentId,String rebateRule){
        String agentJson = RedisStringUtil.get(RedisKeyConfig.AGENT_PREFIX+agentId);
        AgentInfo agentInfo = JsonUtils.toBean(agentJson,AgentInfo.class);

    }
}
