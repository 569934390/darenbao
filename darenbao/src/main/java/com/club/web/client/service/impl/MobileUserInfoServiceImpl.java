package com.club.web.client.service.impl;

import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.ExceptionHandler;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.web.client.domain.MobileUserInfoDo;
import com.club.web.client.domain.TotalUserBillDo;
import com.club.web.client.service.IMobileUserInfoService;
import com.club.web.client.vo.MobileUserInfoVo;
import com.club.web.client.vo.TotalUserBillVo;
import com.club.web.common.CityCache;
import com.club.web.common.service.impl.BaseDoServiceImpl;
import com.club.web.util.IdGenerator;
import com.club.web.weixin.util.MD5Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
@Service
public class MobileUserInfoServiceImpl extends BaseDoServiceImpl implements IMobileUserInfoService {
    @Override
    public MobileUserInfoVo saveOpUpdate(MobileUserInfoVo mobileUserInfo) throws BaseAppException {
        if (StringUtils.isEmpty(mobileUserInfo.getUserId())){
            //验证手机号码唯一性
            MobileUserInfoDo mobileUserInfoDo=BeanUtils.copy(mobileUserInfo, MobileUserInfoDo.class);
            MobileUserInfoVo searchUser = mobileUserInfoDo.selectOne();
            if (searchUser!=null){
                ExceptionHandler.publishMsg("该手机号已注册，请更换手机号再试！");
            }
            mobileUserInfo.setPassword(MD5Util.encode(mobileUserInfo.getMobile()));
            mobileUserInfo.setLoginName(mobileUserInfo.getMobile());
            //工号生成

            //平安银行接口

            mobileUserInfo.setRealName("游客");
            mobileUserInfo.setUserId(IdGenerator.getDefault().nextId());
            mobileUserInfo.setGender(1);
            mobileUserInfo.setPortrait("http://o7omf0udc.bkt.clouddn.com/default.png");
            mobileUserInfo.setNativeProvince("350000");
            mobileUserInfo.setNativeProvinceName("福建");
            mobileUserInfo.setNativeCity("350200");
            mobileUserInfo.setNativeCityName("厦门市");
            List<String> photoInfoList = new ArrayList<String>();
            if (!StringUtils.isEmpty(mobileUserInfo.getPhotos())){
                if(mobileUserInfo.getPhotos().indexOf(",")>0){
                    for (String photo : mobileUserInfo.getPhotos().split(",")){
                        photoInfoList.add(photo);
                    }
                }else {
                    photoInfoList.add(mobileUserInfo.getPhotos());
                }
            }
            mobileUserInfo.setPhotoInfoList(null);
            mobileUserInfo.setCreateTime(new Date());
            mobileUserInfo.setPhotoInfoList(photoInfoList);
            mobileUserInfo.setIsBusy("0");   //空闲状态
            mobileUserInfo.setInvitationCode(mobileUserInfo.getMobile());
            mobileUserInfo.setPickState("1");
            mobileUserInfoDo=BeanUtils.copy(mobileUserInfo, MobileUserInfoDo.class);
            mobileUserInfoDo.insert();
//            RedisStringUtil.setRedis(RedisKeyConfig.USER_PREFIX+id, StringUtils.convertObjectToMap(mobileUserInfo),false);
//            //在redis中存储用户关系
//            UserRela userRela = new UserRela();
//            userRela.setRelaId(UUIDUtils.getUUID());
//            userRela.setUserId(id);
////            this.getByParentUserId(mobileUserInfo.getParentUserId(), userRela, 1);
////            this.getByAgentId(mobileUserInfo.getBrandAgentId(),userRela,1);
////            userRelaService.addNewRela(userRela);
//            //生成总帐单
            TotalUserBillVo totalUserBill = new TotalUserBillVo();
            totalUserBill.setUserId(mobileUserInfo.getUserId());
            BeanUtils.copy(totalUserBill, TotalUserBillDo.class).insert();
        }else {
//            String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+mobileUserInfo.getUserId());
//            if (StringUtils.isEmpty(userJson)){
//                throw new Exception("该账号存在异常，请重新登陆！");
//            }
            //验证手机号码唯一性
            MobileUserInfoDo mobileUserInfoDo=BeanUtils.copy(mobileUserInfo,MobileUserInfoDo.class);
            MobileUserInfoVo searchUser = mobileUserInfoDo.selectOne();
            if (searchUser!=null){
                ExceptionHandler.publishMsg("该手机号已注册，请更换手机号再试！");
            }
            mobileUserInfo.setNativeCityName(CityCache.getNativeCityName(mobileUserInfo.getNativeCity()));
            BeanUtils.copyProperties(mobileUserInfo, mobileUserInfoDo);
            mobileUserInfoDo.update();
            mobileUserInfo.setLoginName(mobileUserInfo.getMobile());
        }
        return mobileUserInfo;
    }

    @Override
    public MobileUserInfoVo getByInvitationCode(String invitationCode) throws BaseAppException {
        MobileUserInfoDo mobileUserInfoDo=new MobileUserInfoDo();
        mobileUserInfoDo.setInvitationCode(invitationCode);
        return mobileUserInfoDo.selectOne();
    }
}
