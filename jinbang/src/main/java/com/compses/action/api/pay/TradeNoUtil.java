package com.compses.action.api.pay;

import com.compses.util.TenpayUtil;

/**
 * Created by jocelynsuebb on 2016/4/13.
 */
public class TradeNoUtil {

    /**
     * 获取订单编号
     * @return
     */
    public static String getTradeNo(){
        String currTime = TenpayUtil.getCurrTime();
        //8位日期
        String strTime = currTime.substring(8, currTime.length());
        //四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        //10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        //订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
        String out_trade_no = strReq;
        return out_trade_no;
    }
}
