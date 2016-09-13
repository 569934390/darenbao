package com.compses.action.api.base;

import com.compses.action.common.BaseCommonController;
import com.compses.common.Constants;
import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.MobileUserInfo;
import com.compses.model.TotalUserBill;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.service.api.statistics.TotalUserBillService;
import com.compses.util.Base64ToImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Administrator on 2016/7/17 0017.
 */

@Controller
@RequestMapping("mobileUserInfo")
public class MobileUserInfoController extends BaseCommonController{

    @Autowired
    private MobileUserInfoService mobileUserInfoService;
    @Autowired
    private TotalUserBillService totalUserBillService;


    //保存与更新
    @RequestMapping(value="saveOrUpdate.do" )
    @ResponseBody
    public ResultData saveOrUpdate(MobileUserInfo mobileUserInfo ){
        ResultData resultData = new ResultData();
        try {
            mobileUserInfo = mobileUserInfoService.saveOpUpdate(mobileUserInfo);
            resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
            resultData.setReturnMsg(true,"保存成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"保存失败！");
        }
        return resultData;
    }

    //根据id获取用户信息
    @RequestMapping(value="getById.do")
    @ResponseBody
    public ResultData getById(String id ){
        ResultData resultData = new ResultData();
        try {
            MobileUserInfo mobileUserInfo = mobileUserInfoService.getById(id);
            resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    /**
     * 上传头像
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="uploadPhoto.do")
    @ResponseBody
    public ResultData uploadPhoto(HttpServletRequest request,HttpServletResponse response){
        ResultData resultData = new ResultData();
        try {
            Enumeration<String> parameters = request.getParameterNames();
            String rootPath = Constants.getContextProperty("photoType").toString();
            String customerPath = Constants.getContextProperty("userImage").toString();
            String userId = request.getParameter("userId"); //用户id
            String totalPath = rootPath+customerPath+ File.separator+userId+ File.separator+"head"+ File.separator;
            File f = new File(totalPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            while (parameters.hasMoreElements()) {
                String name = parameters.nextElement();
                if(name.contains("image")){
                    String photoName = name + ".png";
                    String value = request.getParameter(name);
                    String path = totalPath + photoName;
                    Base64ToImage.base64ToImage(value, path);
                    MobileUserInfo mobileUserInfo = new MobileUserInfo();
                    mobileUserInfo.setUserId(userId);
                    mobileUserInfo.setPortrait(customerPath+ File.separator+userId+ File.separator+"head"+ File.separator+photoName);
                    mobileUserInfoService.update(mobileUserInfo);
                }
            }
//                resultData.putEntity(CustomerInfo.class, customerInfo);
            resultData.setReturnMsg(true,"上传成功！");
        }catch (Exception e){
            resultData.setReturnMsg(false, "上传失败！");
        }
        return resultData;
    }


    @RequestMapping(value="getMobileInfoByUserId.do")
    @ResponseBody
    public ResultData getMobileInfoByUserId(String userId){
        ResultData resultData = new ResultData();
        try {
            MobileUserInfo mobileUserInfo = mobileUserInfoService.getById(userId);
            resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    //我的帮帮
    @RequestMapping(value="getSubordinate.do")
    @ResponseBody
    public ResultData getSubordinate(String userId){
        ResultData resultData = new ResultData();
        try {
            List<MobileUserInfo> mobileUserInfoList = mobileUserInfoService.getSubordinate(userId);
            resultData.putEntities(MobileUserInfo.class,mobileUserInfoList);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }


    @RequestMapping(value="getByIdWithRela.do")
    @ResponseBody
    public ResultData getByIdWithRela(String userId ,String targetUserId){
        ResultData resultData = new ResultData();
        try {
            MobileUserInfo mobileUserInfo = mobileUserInfoService.getByIdWithRela(userId,targetUserId);
            resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="getBalance.do")
    @ResponseBody
    public ResultData getBalance(String userId){
        ResultData resultData = new ResultData();
        try {
            TotalUserBill userBill = totalUserBillService.getByUserId(userId);
            MobileUserInfo mobileUserInfo = new MobileUserInfo();
            mobileUserInfo.setBalance(userBill.getBalance());
            mobileUserInfo.setToSettlementMoney(userBill.getToSettlementMoney());
            mobileUserInfo.setSettlementedMoney(userBill.getSettlementedMoney());
            resultData.putEntity(MobileUserInfo.class,mobileUserInfo);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="getFirstSub.do")
    @ResponseBody
    public Page<MobileUserInfo> getFirstSub(String userId,String level,Page<MobileUserInfo> page){
        Page<MobileUserInfo> mobileUserInfoPage = mobileUserInfoService.getFirstSub(userId,page,level);
        return mobileUserInfoPage;
    }

}
