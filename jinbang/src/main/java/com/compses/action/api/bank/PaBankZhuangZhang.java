package com.compses.action.api.bank;

import com.compses.constants.PabankConstants;
import com.compses.model.PaBankZhuanZhangModel;
import com.compses.util.PaBankUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by jocelynsuebb on 16/9/9.
 */
public class PaBankZhuangZhang {
    public static void main(String[] args) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        PaBankZhuanZhangModel paBankZhuanZhangModel = new PaBankZhuanZhangModel();
        parameters.put("ThirdVoucher", PaBankUtil.getThirdLogNo());
        parameters.put("CcyCode", "RMB");
        parameters.put("OutAcctNo", "11000593657501"); //付款人账户
        parameters.put("OutAcctName", "平安测试保险"); //付款人名称
        parameters.put("OutAcctAddr", "");
        parameters.put("InAcctBankNode", "");
        parameters.put("InAcctRecCode", "");
        parameters.put("InAcctNo", "6216260000200001767");//收款人账户
        parameters.put("InAcctName", "葛盛涛");
        parameters.put("InAcctBankName", "平安银行"); //收款人开户行名称
        parameters.put("TranAmount", "0.01"); //转出金额
        parameters.put("AmountCode", "");
        parameters.put("UseEx", "");
        parameters.put("UnionFlag", "1");//行内跨行标志 1：行内转账，0：跨行转账
        parameters.put("SysFlag", "");
        parameters.put("AddrFlag", "1"); //同城/异地标志 “1”—同城   “2”—异地；若无法区分，可默认送1-同城
        parameters.put("MainAcctNo", "");

        String tranMessageBodyXml = PaBank.getDirectRequestXml(parameters);

        Map retKeyDict = new HashMap();
        try {
            PaBank.requestTCP(tranMessageBodyXml, PabankConstants.KHDM, PabankConstants.TRADE_ZHUANZHANG,retKeyDict,PabankConstants.TRAN_SYS_BANK_DIRECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
