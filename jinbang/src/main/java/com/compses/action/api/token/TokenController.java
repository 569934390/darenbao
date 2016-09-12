package com.compses.action.api.token;

import com.compses.framework.ResultData;
import com.compses.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jocelynsuebb on 16/7/26.
 */
@Controller
@RequestMapping("token")
public class TokenController {

    @RequestMapping(value = "getQiNiuUploadToken.do")
    @ResponseBody
    public ResultData getQiNiuUploadToken(){
        ResultData resultData = new ResultData();
        try {
            String token = QiNiuUtil.getQiNiuUploadToken();
            resultData.put("token",token);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"请求获取七牛Token失败！");
        }
        return resultData;
    }
}
