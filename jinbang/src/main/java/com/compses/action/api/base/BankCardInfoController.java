package com.compses.action.api.base;

import com.compses.constants.OperationCategoryConsants;
import com.compses.dto.CashDetailsDto;
import com.compses.framework.ResultData;
import com.compses.model.BankCardInfo;
import com.compses.model.BillRecord;
import com.compses.service.api.base.BankCardInfoService;
import com.compses.service.api.statistics.BillRecordService;
import com.compses.util.LjSmsClient;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23 0023.
 */

@Controller
@RequestMapping("bankCard")
public class BankCardInfoController {
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private BillRecordService billRecordService;

    public static Map<String,String> phoneCodeCache = new HashMap<String, String>();

    //绑卡
    @RequestMapping(value="save.do" )
    @ResponseBody
    public ResultData save(String mobile,String verifyCode,BankCardInfo bankCardInfo){
        ResultData resultData = new ResultData();
        try {
            if(!StringUtils.isEmpty(mobile)){
                String verifyCodeSend = getVerifyCode(mobile);
                if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
                    bankCardInfo = bankCardInfoService.save(bankCardInfo);
                    resultData.putEntity(BankCardInfo.class, bankCardInfo);
                    resultData.setReturnMsg(true, "保存成功！");
                }else {
                    resultData.setReturnMsg(false, "验证码错误！");
                }
            }else{
                resultData = new ResultData(false,"您输入的手机号码为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"保存失败!");
        }
        return resultData;
    }

    //根据用户获取绑卡列表
    @RequestMapping(value="listByUserId.do" )
    @ResponseBody
    public ResultData listByUserId(String userId){
        ResultData resultData = new ResultData();
        try {
            List<BankCardInfo> result = bankCardInfoService.getByUserId(userId);
            resultData.putEntities(BankCardInfo.class,result);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败!");
        }
        return resultData;
    }

    //删卡
    @RequestMapping(value="delete.do" )
    @ResponseBody
    public ResultData delete(String cardId){
        ResultData resultData = new ResultData();
        try {
            bankCardInfoService.delete(cardId);
            resultData.setReturnMsg(true,"删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"删除失败!");
        }
        return resultData;
    }

    //修改支付密码
    @RequestMapping(value="updatePayPWD.do" )
    @ResponseBody
    public ResultData updatePayPWD(String userId,String oldPwd,String pwd){
        ResultData resultData = new ResultData();
        try {
            int res =bankCardInfoService.updatePayPWD(userId,oldPwd, pwd);
            if (res==1){
                resultData.setReturnMsg(true,"设置成功！");
            }else {
                resultData.setReturnMsg(false,"密码验证失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"设置失败!");
        }
        return resultData;
    }

    @RequestMapping(value="Verify.do" )
    @ResponseBody
    public ResultData Verify(String userId,String pwd){
        ResultData resultData = new ResultData();
        try {
            boolean result = bankCardInfoService.Verify(userId, pwd);
            if (result) {
                resultData.setReturnMsg(true,"验证成功！");
            }else {
                resultData.setReturnMsg(false,"验证失败!");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"验证失败!");
        }
        return resultData;
    }

    /**
     * 根据手机号获取服务端验证码
     * @param mobile
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    @RequestMapping(value = "verifyPhone.do" )
    @ResponseBody
    public ResultData verifyPhone(String mobile) {
        ResultData resultData = new ResultData();
        try {
            if(!StringUtils.isEmpty(mobile)){
                phoneCodeCache.remove(mobile);
                String verifyCode = "";
                if (phoneCodeCache.containsKey(mobile)){
                    long delay = Long.parseLong(phoneCodeCache.get(mobile + "_time"));
                    if (new Date().getTime()-delay>3600*1000){
                        verifyCode = this.verifyCode(mobile);
                    }
                    else{
                        verifyCode = phoneCodeCache.get(mobile);
                    }
                }
                else{
                    verifyCode = this.verifyCode(mobile);
                }
                LjSmsClient client = new LjSmsClient();
                client.sendSms(mobile, "验证码"+verifyCode+"请注意查收。【近帮】");
                resultData.setReturnMsg(true, "验证码已发送至手机！");
            }
            else {
                resultData.setReturnMsg(false, "电话号码无效！");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false, "短信发送失败！");
        }
        return resultData;
    }

    //忘记密码
    @RequestMapping(value="VerifyCode.do" )
    @ResponseBody
    public ResultData verifyCode(String mobile,String verifyCode){
        ResultData resultData = new ResultData();
        try {
            if(!StringUtils.isEmpty(mobile)){
                String verifyCodeSend = getVerifyCode(mobile);
                if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
                    resultData.setReturnMsg(true, "操作成功！");
                }else {
                    resultData.setReturnMsg(false, "验证码错误！");
                }
            }else{
                resultData = new ResultData(false,"您输入的手机号码为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败!");
        }
        return resultData;
    }

    //忘记密码
    @RequestMapping(value="forgetPWD.do" )
    @ResponseBody
    public ResultData forgetPWD(String userId,String mobile,String verifyCode,String pwd){
        ResultData resultData = new ResultData();
        try {
            if(!StringUtils.isEmpty(mobile)){
                String verifyCodeSend = getVerifyCode(mobile);
                if(!StringUtils.isEmpty(verifyCodeSend) && verifyCodeSend.equals(verifyCode)){
                    bankCardInfoService.forgetPWD(userId, pwd);
                    resultData.setReturnMsg(true, "操作成功！");
                }else {
                    resultData.setReturnMsg(false, "验证码错误！");
                }
            }else{
                resultData = new ResultData(false,"您输入的手机号码为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败!");
        }
        return resultData;
    }


    private String verifyCode(String phoneCode){
        String verifyCode = StringUtils.random(4);
        phoneCodeCache.put(phoneCode,verifyCode);
        phoneCodeCache.put(phoneCode+"_time",new Date().getTime()+"");
        return verifyCode;
    }

    private String getVerifyCode(String phoneCode){
        return phoneCodeCache.get(phoneCode);
    }

    //提现
    @RequestMapping(value="cash.do" )
    @ResponseBody
    public ResultData cash(BillRecord billRecord,String pwd){
        ResultData resultData = new ResultData();
        try {
            int res = billRecordService.addCash(billRecord,pwd);
            if (res == 1){
                resultData.setReturnMsg(true,"操作成功，等待审核通过！");
            }else if (res ==0){
                resultData.setReturnMsg(false,"密码错误！");
            }else if(res==-1){
                resultData.setReturnMsg(false,"余额不足！");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"提现失败!");
        }
        return resultData;
    }

    //提现记录
    @RequestMapping(value="listCashHistory.do" )
    @ResponseBody
    public ResultData listCashHistory(String userId){
        ResultData resultData = new ResultData();
        try {
            List<BillRecord> recordList = billRecordService.listByCondition(userId,OperationCategoryConsants.OPERATION_CATEGORY_CASH);
            resultData.putEntities(BillRecord.class,recordList);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败!");
        }
        return resultData;
    }


    //交易记录
    @RequestMapping(value="listAllRecord.do" )
    @ResponseBody
    public ResultData listAllRecord(String userId){
        ResultData resultData = new ResultData();
        try {
            List<BillRecord> recordList = billRecordService.listAllRecord(userId);
            resultData.putEntities(BillRecord.class,recordList);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败!");
        }
        return resultData;
    }

    @RequestMapping(value="judgeExistPayPwd.do" )
    @ResponseBody
    public ResultData judgeExistPayPwd(String userId){
        ResultData resultData = new ResultData();
        try {
            boolean result = bankCardInfoService.judgeExistPayPwd(userId);
            if (result) {
                resultData.setReturnMsg(true,"存在支付密码！");
            }else {
                resultData.setReturnMsg(false,"不存在支付密码!");
            }
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败!");
        }
        return resultData;
    }

    //提现详情
    @RequestMapping(value="cashDetails.do" )
    @ResponseBody
    public ResultData cashDetails(String recordId) {
        ResultData resultData = new ResultData();
        try {
            CashDetailsDto cashDetailsDto = bankCardInfoService.getDetails(recordId);
            resultData.putEntity(CashDetailsDto.class,cashDetailsDto);
            resultData.setReturnMsg(true, "获取成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setReturnMsg(false, "获取失败!");
        }
        return resultData;
    }

}
