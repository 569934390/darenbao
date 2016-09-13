package com.compses.action.api.bank;

import com.compses.constants.PabankConstants;
import com.compses.framework.ResultData;
import com.compses.model.MobileUserInfo;
import com.compses.model.PaBankModel;
import com.compses.model.PaBankZhuanZhangModel;
import com.compses.model.ReportInfo;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.util.PaBankUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jocelynsuebb on 16/9/5.
 */
@Controller
@RequestMapping("paBank")
public class PaBankController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MobileUserInfoService mobileUserInfoService;

    @RequestMapping(value="kaihu.do" )
    @ResponseBody
    public ResultData kaihu(PaBankModel paBankModel){
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        ResultData resultData = new ResultData();
        try {
            parameters.put("userName", paBankModel.getUserName());
            parameters.put("cardNumber", paBankModel.getCardNumber());
            parameters.put("mobile", paBankModel.getMobile());
            parameters.put("channelType", paBankModel.getChannelType());
            parameters.put("umCode", "");
            String tranMessageBodyXml = PaBank.getRequestXml(parameters);
            Map retKeyDict = new HashMap();
            PaBank.requestTCP(tranMessageBodyXml, PabankConstants.KHDM, PabankConstants.TRADE_KAIHU,retKeyDict,PabankConstants.TRAN_SYS_CHENGZI);
            if(retKeyDict.size()>0){
                String state = retKeyDict.get("state").toString();
                if(state.equals(PabankConstants.PABANK_ERROR_CODE_SUCCESS)){
                    //激活橙子银行卡
                    String cardNumber = retKeyDict.get("cardNo").toString();
                    SortedMap<Object, Object> jihuoParameters = new TreeMap<Object, Object>();
                    jihuoParameters.put("cardNo",cardNumber);
                    jihuoParameters.put("callBackUrl",PabankConstants.PABANK_CALLBACK_URL);
                    Map jihuoRetKeyDict = new HashMap();
                    PaBank.requestTCP(tranMessageBodyXml, PabankConstants.KHDM, PabankConstants.TRADE_JIHUO,jihuoRetKeyDict,PabankConstants.TRAN_SYS_CHENGZI);
                    if(jihuoRetKeyDict.size()>0){
                        String jihuoState = jihuoRetKeyDict.get("state").toString();
                        if(jihuoState.equals(PabankConstants.PABANK_ERROR_CODE_SUCCESS)){
                            paBankModel.setReturnUrl(jihuoRetKeyDict.get("returnUrl").toString());
                            resultData = new ResultData(PaBankModel.class,paBankModel);
                            //TODO 更新下用户实名认证时间,就算是用户实名认证成功,保存橙子银行卡号cardNumber
                            //修改用户认证状态
                            MobileUserInfo userInfo = mobileUserInfoService.getById(paBankModel.getUserId());
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            String authenticationTime = df.format(new Date());
                            userInfo.setAuthenticationTime(authenticationTime);
                            userInfo.setSupacctid(cardNumber);
                            mobileUserInfoService.update(userInfo);
                            resultData.setReturnMsg(true, "获取平安银行提现H5成功！");
                        }else {
                            resultData.setReturnMsg(false, "操作失败！");
                            logger.error("橙子银行账户激活失败,错误码:"+retKeyDict.get("errorCode")+" , 错误信息:"+retKeyDict.get("errorMessage"));
                        }
                    }
                }else{
                    resultData.setReturnMsg(false, "操作失败！");
                    logger.error("橙子银行开户失败,错误码:"+retKeyDict.get("errorCode")+" , 错误信息:"+retKeyDict.get("errorMessage"));
                }
            }else{
                resultData.setReturnMsg(false, "提现失败！");
            }
        }catch (Exception e){
            resultData.setReturnMsg(false, "操作失败！");
            e.printStackTrace();
        }
        return resultData;
    }


    /**
     * 银企转账
     * @param paBankZhuanZhangModel
     * @return
     */
    @RequestMapping(value="transferAccounts.do" )
    @ResponseBody
    public ResultData transferAccounts(PaBankZhuanZhangModel paBankZhuanZhangModel){
        ResultData resultData = new ResultData();
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("ThirdVoucher", PaBankUtil.getThirdLogNo());
        parameters.put("CcyCode", PabankConstants.PABANK_CCY_CODE);
        parameters.put("OutAcctNo", paBankZhuanZhangModel.getOutAcctNo());
        parameters.put("OutAcctName", paBankZhuanZhangModel.getOutAcctName());
        parameters.put("OutAcctAddr", "");
        parameters.put("InAcctBankNode", "");
        parameters.put("InAcctRecCode", "");
        parameters.put("InAcctNo", paBankZhuanZhangModel.getInAcctNo());
        parameters.put("InAcctName", paBankZhuanZhangModel.getInAcctName());
        parameters.put("InAcctBankName", paBankZhuanZhangModel.getInAcctBankName());
        parameters.put("TranAmount", paBankZhuanZhangModel.getTranAmount());
        parameters.put("AmountCode", "");
        parameters.put("UseEx", "");
        parameters.put("UnionFlag", paBankZhuanZhangModel.getUnionFlag());
        parameters.put("SysFlag", "");
        parameters.put("AddrFlag", paBankZhuanZhangModel.getAddrFlag());
        parameters.put("MainAcctNo", "");


        try {
            String tranMessageBodyXml = PaBank.getDirectRequestXml(parameters);

            Map retKeyDict = new HashMap();
            PaBank.requestTCP(tranMessageBodyXml, PabankConstants.KHDM, PabankConstants.TRADE_ZHUANZHANG,retKeyDict,PabankConstants.TRAN_SYS_BANK_DIRECT);
            if(retKeyDict.size()>0){
                //TODO 保存转账记录 -- 转账中
            }else{
                resultData.setReturnMsg(false, "操作失败！");
            }
        } catch (Exception e) {
            resultData.setReturnMsg(false, "操作失败！");
            e.printStackTrace();
        }
        return resultData;
    }
}
