package com.compses.service.api.statistics;

import com.compses.constants.OperationCategoryConsants;
import com.compses.constants.OrderConstants;
import com.compses.constants.RoleConstants;
import com.compses.constants.RuleConstants;
import com.compses.dto.RuleDto;
import com.compses.model.*;
import com.compses.redis.service.RedisSystemParameter;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisSystemParameterUtil;
import com.compses.service.api.base.AgentInfoService;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.service.api.order.OrderInfoService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
@Service
@Transactional
public class StatisticsSercive extends BaseCommonService{
    @Autowired
    private TotalUserBillService totalUserBillService;
    @Autowired
    private BillRecordService billRecordService;
    @Autowired
    private MobileUserInfoService mobileUserInfoService;
    @Autowired
    private AgentInfoService agentInfoService;


    public void updateStatisticsOrder(OrderInfo orderInfo){
        orderInfo.setOrderPrice(orderInfo.getOrderPrice()*10000);
        //获取订单信息内容
        String reqUserId = orderInfo.getPublisherId();          //买方
        String serviceUserId = orderInfo.getPickerId();         //卖方
        //平台返利
        this.updateCalculation(orderInfo, RuleConstants.ORDER_TYPE_PLATFORM, RuleConstants.PLATFORM_REBATE, OrderConstants.PLATFORM_ID, OrderConstants.BENEFIT_TYPE_PLATFORM);
        //计算分红比
        //1.买方
        this.updateStatisticsByCategory(reqUserId, RuleConstants.ORDER_TYPE_BUYER, orderInfo);
        //2.卖方
        this.updateStatisticsByCategory(serviceUserId, RuleConstants.ORDER_TYPE_SELLER, orderInfo);
        //3.服务方收益
        this.updateStatisticsByCategory(serviceUserId, null, orderInfo);
    }

    public void updateStatisticsByCategory(String userId,String category,OrderInfo orderInfo){
        MobileUserInfo userInfo = mobileUserInfoService.getById(userId);
        if (category==null){
            String ruleStr = RedisSystemParameterUtil.get(RedisKeyConfig.RULE_PREFIX + orderInfo.getOrderType());
            OrderReturnRule rule = JsonUtils.toBean(ruleStr, OrderReturnRule.class);
            String ruleContent = rule.getRuleContent();
            Map<String, Object> ruleMap = JsonUtils.toMap(ruleContent);
            double channelRebate = Double.parseDouble(""+ruleMap.get(RuleConstants.CHANNEL_REBATE));
            double platformRebate = Double.parseDouble(""+ruleMap.get(RuleConstants.PLATFORM_REBATE));
            double rebate = orderInfo.getOrderPrice()*((100-channelRebate-platformRebate)/100);
            this.updateSettlement(orderInfo, userId, OrderConstants.BENEFIT_TYPE_USER, rebate);
        }else {
            //会员
            //一级
            String parentUserId = this.updateStatisticsByCategory(userId,category,OrderConstants.BENEFIT_TYPE_USER,RuleConstants.USER_DISTRIBUTION_FIRST,orderInfo);
            //二级
            if (!StringUtils.isEmpty(parentUserId)){
                parentUserId = this.updateStatisticsByCategory(userId,category,OrderConstants.BENEFIT_TYPE_USER,RuleConstants.USER_DISTRIBUTION_SECOND,orderInfo);
            }
            //三级
            if (!StringUtils.isEmpty(parentUserId)){
                parentUserId = this.updateStatisticsByCategory(userId,category,OrderConstants.BENEFIT_TYPE_USER,RuleConstants.USER_DISTRIBUTION_THIRD,orderInfo);
            }
            //运营商
            //品牌
            String parentAgentId = this.updateStatisticsByCategory(userInfo.getBrandAgentId(),category,OrderConstants.BENEFIT_TYPE_AGENT,RuleConstants.AGENT_TYPE_BAND,orderInfo);
            //站长
            parentAgentId = this.updateStatisticsByCategory(parentAgentId,category,OrderConstants.BENEFIT_TYPE_AGENT,RuleConstants.AGENT_TYPE_STATIONMASTER,orderInfo);
            //地区
            parentAgentId = this.updateStatisticsByCategory(parentAgentId,category,OrderConstants.BENEFIT_TYPE_AGENT,RuleConstants.AGENT_TYPE_AREA,orderInfo);
            //中国区
            this.updateStatisticsByCategory(parentAgentId,category,OrderConstants.BENEFIT_TYPE_AGENT,RuleConstants.AGENT_TYPE_CHINA,orderInfo);
        }
    }

    public String updateStatisticsByCategory(String userId,String category,String roleType,String rebateCategory,OrderInfo orderInfo){
        if (roleType.equals(OrderConstants.BENEFIT_TYPE_USER)){
            //获取上级对象
            MobileUserInfo user = mobileUserInfoService.getParent(userId);
            if (user!=null){
                this.updateCalculation(orderInfo, category, rebateCategory, user.getUserId(), OrderConstants.BENEFIT_TYPE_USER);
                return user.getParentUserId();
            }else {
               if (rebateCategory.equals(RuleConstants.USER_DISTRIBUTION_FIRST)){
                   double rebate =updateToCalculationRebate(orderInfo,category,RuleConstants.USER_DISTRIBUTION_FIRST);
                   rebate +=updateToCalculationRebate(orderInfo,category,RuleConstants.USER_DISTRIBUTION_SECOND);
                   rebate +=updateToCalculationRebate(orderInfo,category,RuleConstants.USER_DISTRIBUTION_THIRD);
                   this.updateSettlement(orderInfo,OrderConstants.PLATFORM_ID,RuleConstants.ORDER_TYPE_PLATFORM,rebate);
               }else if (rebateCategory.equals(RuleConstants.USER_DISTRIBUTION_SECOND)){
                   double rebate =updateToCalculationRebate(orderInfo,category,RuleConstants.USER_DISTRIBUTION_SECOND);
                   rebate +=updateToCalculationRebate(orderInfo,category,RuleConstants.USER_DISTRIBUTION_THIRD);
                   this.updateSettlement(orderInfo,OrderConstants.PLATFORM_ID,RuleConstants.ORDER_TYPE_PLATFORM,rebate);
               }else if (rebateCategory.equals(RuleConstants.USER_DISTRIBUTION_THIRD)){
                   double rebate =updateToCalculationRebate(orderInfo,category,RuleConstants.USER_DISTRIBUTION_THIRD);
                   this.updateSettlement(orderInfo,OrderConstants.PLATFORM_ID,RuleConstants.ORDER_TYPE_PLATFORM,rebate);
               }
                return null;
            }
        }else {
            //获取上级对象
            AgentInfo agentInfo = agentInfoService.getParent(userId);
            if (agentInfo!=null){
                this.updateCalculation(orderInfo, category, rebateCategory, agentInfo.getAgentId(), OrderConstants.BENEFIT_TYPE_USER);
                return agentInfo.getAgentId();
            }else {
                return null;
            }
        }
    }

    public void updateCalculation(OrderInfo orderInfo ,String category,String rebateCategory,String beneficiary,String beneficiaryType){
        double rebate =updateToCalculationRebate(orderInfo,category,rebateCategory);
        this.updateSettlement(orderInfo,beneficiary,beneficiaryType,rebate);
    }

    public void updateSettlement(OrderInfo orderInfo,String beneficiary,String beneficiaryType,double rebate){
        //交易记录保存
        BillRecord billRecord = new BillRecord();
        billRecord.setPayUserId(orderInfo.getPublisherId());
        billRecord.setRoleType(beneficiaryType);
        billRecord.setReceivingUserId(beneficiary);
        billRecord.setOrderCode(orderInfo.getOrderCode());
        billRecord.setTradingAmount(orderInfo.getOrderPrice());
        billRecord.setTradingDate(new Date());
        billRecord.setSettlementMoney(rebate);
        billRecord.setOperationCategory(OperationCategoryConsants.OPERATION_CATEGORY_REBATE);
        billRecordService.save(billRecord);
        //修改待结算金额
        TotalUserBill totalUserBill = totalUserBillService.getByUserId(beneficiary);
        totalUserBill.setTotalIncomeMoney(totalUserBill.getTotalIncomeMoney() + rebate);
        totalUserBill.setBalance(totalUserBill.getToSettlementMoney() + rebate);
        totalUserBillService.update(totalUserBill);
    }

    public double updateToCalculationRebate(OrderInfo orderInfo,String category,String rebateCategory){
        //获取返利规则
        String ruleStr = RedisSystemParameterUtil.get(RedisKeyConfig.RULE_PREFIX+orderInfo.getOrderType());
        OrderReturnRule rule = JsonUtils.toBean(ruleStr, OrderReturnRule.class);
        String ruleContent = rule.getRuleContent();
        Map<String, Object> ruleMap = JsonUtils.toMap(ruleContent);
        double rebate = 0;
        if (category.equals(RuleConstants.ORDER_TYPE_PLATFORM)){
            double platformRebate = Double.parseDouble(""+ruleMap.get(RuleConstants.PLATFORM_REBATE));
            rebate = orderInfo.getOrderPrice()*(platformRebate/100);
        }else if (category.equals(RuleConstants.ORDER_TYPE_BUYER) || category.equals(RuleConstants.ORDER_TYPE_SELLER)){
            double channelRebate = Double.parseDouble("" + ruleMap.get(RuleConstants.CHANNEL_REBATE));
            double actualProportion = Double.parseDouble("" + ruleMap.get(category+rebateCategory));
            //计算返利金额
            rebate = orderInfo.getOrderPrice()*(channelRebate/100)*(actualProportion/100);
        }
        return rebate;
    }

}
