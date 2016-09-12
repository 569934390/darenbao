package com.compses.constants;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class OrderConstants {

    /*--------订单类型----------*/

    //2元订单
    public static final String ORDER_CATEGORY_2YUAN = "1";
    //同城订单
//    public static final String ORDER_CATEGORY_SAMECITY ="1";
    //异城订单
//    public static final String ORDER_CATEGORY_DIFFERENTCITY ="2";
    //五元橙子卡
    public static final String ORDER_CATEGORY_ORANGE="3";
    //会员订单
    public static final String ORDER_CATEGORY_MEMBER="1";

    /*--------订单状态----------*/

    //待付款
    public static final String ORDER_STATUS_WAITPAY ="1";
    //已付款
    public static final String ORDER_STATUS_HASPAY="2";
    //服务中
    public static final String ORDER_STATUS_SERVING="3";
    //待评价
    public static final String ORDER_STATUS_WAITCOMMENT="4";
    //已结束
    public static final String ORDER_STATUS_END="5";
    //退款中
    public static final String ORDER_STATUS_REFUNDING="-1";
    //已退款
    public static final String ORDER_STATUS_REFUND="-2";
    //已取消（主动）
    public static final String ORDER_STATUS_CANCEL_ZD="-3";
    //已取消（被动）超时
    public static final String ORDER_STATUS_CANCEL_BD="-4";
    //同意
    public static final String CANCEL_TYPE_AGREE="1";
    //不同意
    public static final String CANCEL_TYPE_NOTAGREE="0";

    public static final String EXECUTION_STATUS_ACCEPTING="0";
    //执行状态-已执行
    public static final String EXECUTION_STATUS_ACCEPTED="1";


    /*--------------------受益方类型------------------*/
    //用户
    public static final String BENEFIT_TYPE_USER="1";
    //运营商
    public static final String BENEFIT_TYPE_AGENT="0";
    //平台
    public static final String BENEFIT_TYPE_PLATFORM="2";

    public static final String PLATFORM_ID="00000";

}
