package com.compses.service.api.statistics;

import com.compses.constants.RuleConstants;
import com.compses.dao.statistics.IOrderReturnRuleDao;
import com.compses.dto.Page;
import com.compses.model.OrderReturnRule;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisSystemParameterUtil;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
@Service
@Transactional
public class OrderReturnRuleService {
    @Autowired
    private IOrderReturnRuleDao orderReturnRuleDao;

    //获取所有规则
    public List<OrderReturnRule> getAll(){
        OrderReturnRule orderReturnRule = new OrderReturnRule();
        return orderReturnRuleDao.loadData(orderReturnRule);
    }

    public OrderReturnRule addNewRule(OrderReturnRule rule){
        rule.setReturnRuleId(UUIDUtils.getUUID());
        orderReturnRuleDao.saveForUUid(rule);
        RedisSystemParameterUtil.set(RedisKeyConfig.RULE_PREFIX+rule.getOrderCategory()+"_"+rule.getOrderCategory(), JsonUtils.toJson(rule));
        return rule;
    }

    public OrderReturnRule updateRule(OrderReturnRule rule){
        orderReturnRuleDao.update(rule);
        RedisSystemParameterUtil.set(RedisKeyConfig.RULE_PREFIX+rule.getOrderCategory()+"_"+rule.getOrderCategory(), JsonUtils.toJson(rule));
        return rule;
    }

    public List<OrderReturnRule> listByCondition(String orderCategory){
        OrderReturnRule rule = new OrderReturnRule();
        if (!StringUtils.isEmpty(orderCategory)){
            rule.setOrderCategory(orderCategory);
        }
        return orderReturnRuleDao.loadData(rule);
    }

    public Page<OrderReturnRule> getPage(OrderReturnRule rule,Page<OrderReturnRule> page){
        page = orderReturnRuleDao.getPage(rule,page);
        List<OrderReturnRule> ruleList =page.getResult();
        for (OrderReturnRule orderReturnRule:ruleList){
            String ruleContent = orderReturnRule.getRuleContent();
            Map<String, Object> ruleMap = JsonUtils.toMap(ruleContent);
            orderReturnRule.setPlatformRebate(Double.parseDouble(""+ruleMap.get(RuleConstants.PLATFORM_REBATE)));
            orderReturnRule.setChannelRebate(Double.parseDouble("" + ruleMap.get(RuleConstants.CHANNEL_REBATE)));
            //买方
            orderReturnRule.setBuyerChinaZoneAgent(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.AGENT_TYPE_CHINA)));
            orderReturnRule.setBuyerAreaZoneAgent(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.AGENT_TYPE_AREA)));
            orderReturnRule.setBuyerStationmaster(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.AGENT_TYPE_STATIONMASTER)));
            orderReturnRule.setBuyerBandAgent(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.AGENT_TYPE_BAND)));
            orderReturnRule.setBuyerFirstLevel(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.USER_DISTRIBUTION_FIRST)));
            orderReturnRule.setBuyerSecondLevel(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.USER_DISTRIBUTION_SECOND)));
            orderReturnRule.setBuyerThirdLevel(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_BUYER + RuleConstants.USER_DISTRIBUTION_THIRD)));
            //卖方
            orderReturnRule.setSellerChinaZoneAgent(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.AGENT_TYPE_CHINA)));
            orderReturnRule.setSellerAreaZoneAgent(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.AGENT_TYPE_AREA)));
            orderReturnRule.setSellerStationmaster(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.AGENT_TYPE_STATIONMASTER)));
            orderReturnRule.setSellerBandAgent(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.AGENT_TYPE_BAND)));
            orderReturnRule.setSellerFirstLevel(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.USER_DISTRIBUTION_FIRST)));
            orderReturnRule.setSellerSecondLevel(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.USER_DISTRIBUTION_SECOND)));
            orderReturnRule.setSellerThirdLevel(Double.parseDouble("" + ruleMap.get(RuleConstants.ORDER_TYPE_SELLER + RuleConstants.USER_DISTRIBUTION_THIRD)));
        }
        return page;
    }


}
