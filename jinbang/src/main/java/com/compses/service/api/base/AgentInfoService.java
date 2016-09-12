package com.compses.service.api.base;

import com.compses.dao.base.IAgentInfoDao;
import com.compses.model.AgentInfo;
import com.compses.model.DopPrivilegeUser;
import com.compses.model.TotalUserBill;
import com.compses.redis.service.RedisSystemParameter;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.redis.util.RedisSystemParameterUtil;
import com.compses.service.api.statistics.TotalUserBillService;
import com.compses.service.api.system.UserInfoService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

@Service
@Transactional
public class AgentInfoService extends BaseCommonService {

    @Autowired
    private IAgentInfoDao agentInfoDao;
    @Autowired
    private TotalUserBillService totalUserBillService;
    @Autowired
    private UserInfoService userInfoService;

    public AgentInfo add(AgentInfo agentInfo)throws Exception{
        //todo 运营商编号填写
        agentInfo.setCreateDate(new Date());
        agentInfo.setAgentId(UUIDUtils.getUUID());
        agentInfo.setTotalIncome(0);
        agentInfo.setSettleMoney(0);
        agentInfo.setTurnoutMoney(0);
        agentInfo.setStaySettleMoney(0);
        agentInfo.setNativeProvinceName(RedisSystemParameterUtil.get(RedisKeyConfig.PROVINCE_PREFIX + agentInfo.getNativeProvinceId()));
        agentInfo.setNativeCityName(RedisSystemParameterUtil.get(RedisKeyConfig.CITY_PREFIX + agentInfo.getNativeCityId()));
        agentInfo.setNativeAreaName(RedisSystemParameterUtil.get(RedisKeyConfig.AREA_PREFIX+agentInfo.getNativeAreaId()));
        agentInfoDao.saveForUUid(agentInfo);
        //生成账号
        DopPrivilegeUser user = new DopPrivilegeUser();
        user.setRealName(agentInfo.getAgentName());
        user.setPhone(agentInfo.getMobile());
        user.setName(agentInfo.getMobile());
        user.setPassword(Md5PasswordEncoder.md5Encode(Md5PasswordEncoder.md5Encode(agentInfo.getMobile())));
        userInfoService.save(user);
        RedisStringUtil.setRedis(RedisKeyConfig.AGENT_PREFIX + agentInfo.getAgentId(), StringUtils.convertObjectToMap(agentInfo), false);
        //生成总帐单
        TotalUserBill totalUserBill = new TotalUserBill();
        totalUserBill.setUserId(agentInfo.getAgentId());
        totalUserBillService.save(totalUserBill);
        LjSmsClient client = new LjSmsClient();
        client.sendSms(agentInfo.getMobile(), "恭喜您成功成为近帮经销商。【近帮】");
        return agentInfo;
    }

    public AgentInfo update(AgentInfo agentInfo)throws Exception{
        agentInfo.setNativeProvinceName(RedisSystemParameterUtil.get(RedisKeyConfig.PROVINCE_PREFIX + agentInfo.getNativeProvinceId()));
        agentInfo.setNativeCityName(RedisSystemParameterUtil.get(RedisKeyConfig.CITY_PREFIX + agentInfo.getNativeCityId()));
        agentInfo.setNativeAreaName(RedisSystemParameterUtil.get(RedisKeyConfig.AREA_PREFIX+agentInfo.getNativeAreaId()));
        agentInfoDao.update(agentInfo);
        RedisStringUtil.setRedis(RedisKeyConfig.AGENT_PREFIX+agentInfo.getAgentId(), StringUtils.convertObjectToMap(agentInfo),false);
        LjSmsClient client = new LjSmsClient();
        client.sendSms(agentInfo.getMobile(), "注意，刚有人操作了您的个人信息。【近帮】");
        return agentInfo;
    }

    public AgentInfo getById(String id){
        AgentInfo agentInfo = new AgentInfo();
        String agentInfoJson = RedisStringUtil.get(RedisKeyConfig.AGENT_PREFIX+id);
        if (agentInfoJson==null){
            agentInfo.setAgentId(id);
            agentInfo = agentInfoDao.selectOne(agentInfo);
        }else {
            agentInfo = JsonUtils.toBean(agentInfoJson,AgentInfo.class);
        }
        return agentInfo;
    }

    public void deleteById(String id){
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId(id);
        agentInfoDao.delete(agentInfo);
        RedisStringUtil.del(RedisKeyConfig.AGENT_PREFIX+id);
    }

    public AgentInfo getParent(String agentId){
        return agentInfoDao.getParent(agentId);
    }

    public AgentInfo getByMobile(String mobile){
        return agentInfoDao.getByMobile(mobile);
    }
}
