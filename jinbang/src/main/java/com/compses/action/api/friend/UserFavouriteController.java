package com.compses.action.api.friend;

import com.compses.action.common.BaseCommonController;
import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.MobileUserInfo;
import com.compses.model.UserFavourite;
import com.compses.service.api.friend.UserFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */

@Controller
@RequestMapping("userFavourite")
public class UserFavouriteController extends BaseCommonController{
    @Autowired
    private UserFavouriteService userFavouriteService;

    @RequestMapping(value="listPageForFavourite.do" )
    @ResponseBody
    public ResultData listPageForFavourite(String userId,Page<MobileUserInfo> page){
        ResultData resultData = null;
        try {
            Page<MobileUserInfo> userFavouritePage = userFavouriteService.listPageForFavourite(userId,page);
            resultData = new ResultData(MobileUserInfo.class,userFavouritePage);
            resultData.setReturnMsg(true,"获取成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"获取失败！");
        }
        return resultData;
    }

    @RequestMapping(value="addOrDelFavourite.do")
    @ResponseBody
    public ResultData addOrDelFavourite(String userId,String favouriteUserId){
        ResultData resultData = new ResultData();
        try {
            userFavouriteService.addOrDelFavourite(userId,favouriteUserId);
            resultData.setReturnMsg(true,"操作成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultData.setReturnMsg(false,"操作失败！");
        }
        return resultData;
    }


}
