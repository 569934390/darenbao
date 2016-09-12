package com.compses.service.api.base;

import com.compses.dao.base.IMobileUserInfoDao;
import com.compses.dao.impl.base.UserRelaDao;
import com.compses.dto.Page;
import com.compses.model.*;
import com.compses.redis.util.RedisHashSetUtil;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.redis.util.RedisSystemParameterUtil;
import com.compses.service.api.friend.UserFavouriteService;
import com.compses.service.api.provider.ServiceInfoService;
import com.compses.service.api.statistics.TotalUserBillService;
import com.compses.service.api.system.DictService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.Md5PasswordEncoder;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
@Service
@Transactional
public class MobileUserInfoService extends BaseCommonService{

    @Autowired
    private IMobileUserInfoDao mobileUserInfoDao;
    @Autowired
    private ServiceInfoService serviceInfoService;
    @Autowired
    private UserRelaService userRelaService;
    @Autowired
    private AgentInfoService agentInfoService;
    @Autowired
    private UserFavouriteService userFavouriteService;
    @Autowired
    private TotalUserBillService totalUserBillService;


    public MobileUserInfo saveOpUpdate(MobileUserInfo mobileUserInfo)throws Exception{
        if (StringUtils.isEmpty(mobileUserInfo.getUserId())){
            //验证手机号码唯一性
            MobileUserInfo searchUser = mobileUserInfoDao.getByMobile(mobileUserInfo);
            if (searchUser!=null){
                throw new Exception("该手机号已注册，请更换手机号再试！");
            }
            mobileUserInfo.setPassword(Md5PasswordEncoder.md5Encode(mobileUserInfo.getMobile()));
            mobileUserInfo.setLoginName(mobileUserInfo.getMobile());
            //工号生成

            //平安银行接口

            String id = UUIDUtils.getUUID();
            mobileUserInfo.setRealName("游客");
            mobileUserInfo.setUserId(id);
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
            mobileUserInfoDao.saveForUUid(mobileUserInfo);
            RedisStringUtil.setRedis(RedisKeyConfig.USER_PREFIX+id, StringUtils.convertObjectToMap(mobileUserInfo),false);
            //在redis中存储用户关系
            UserRela userRela = new UserRela();
            userRela.setRelaId(UUIDUtils.getUUID());
            userRela.setUserId(id);
//            this.getByParentUserId(mobileUserInfo.getParentUserId(), userRela, 1);
//            this.getByAgentId(mobileUserInfo.getBrandAgentId(),userRela,1);
//            userRelaService.addNewRela(userRela);
            //生成总帐单
            TotalUserBill totalUserBill = new TotalUserBill();
            totalUserBill.setUserId(id);
            totalUserBillService.save(totalUserBill);
        }else {
//            String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+mobileUserInfo.getUserId());
//            if (StringUtils.isEmpty(userJson)){
//                throw new Exception("该账号存在异常，请重新登陆！");
//            }
            //验证手机号码唯一性
            MobileUserInfo searchUser = mobileUserInfoDao.getByMobile(mobileUserInfo);
            if (searchUser!=null){
                throw new Exception("该手机号已注册，请更换手机号再试！");
            }
            mobileUserInfo.setNativeCityName(RedisSystemParameterUtil.get(RedisKeyConfig.CITY_PREFIX + mobileUserInfo.getNativeCity()));
            mobileUserInfoDao.update(mobileUserInfo);
            mobileUserInfo.setLoginName(mobileUserInfo.getMobile());
            RedisStringUtil.setRedis(RedisKeyConfig.USER_PREFIX+mobileUserInfo.getUserId(),StringUtils.convertObjectToMap(mobileUserInfo),false);
        }
        return mobileUserInfo;
    }

    public MobileUserInfo getByInvitationCode(String invitationCode){
        MobileUserInfo mobileUserInfo = mobileUserInfoDao.getByInvitationCode(invitationCode);
        return mobileUserInfo;
    }

    public MobileUserInfo getByParentUserId(String parentUserId,UserRela userRela,int i){
        MobileUserInfo mobileUserInfo = mobileUserInfoDao.getByParentUserId(parentUserId);
        if (mobileUserInfo!= null && i<=3){
            switch (i) {
                case 1:
                userRela.setFirstDistributorPerson(mobileUserInfo.getUserId());
                break;
                case 2:
                userRela.setSecondDistributorPerson(mobileUserInfo.getUserId());
                break;
                case 3:
                userRela.setThirdDistributorPerson(mobileUserInfo.getUserId());
                break;
            }
            if (mobileUserInfo.getInvitationCode()==null){
                return null;
            }
            this.getByParentUserId(mobileUserInfo.getParentUserId(), userRela, i + 1);
            return mobileUserInfo;
        }else {
            return null;
        }
    }

    public AgentInfo getByAgentId(String agentId,UserRela userRela,int i){
        AgentInfo agentInfo = agentInfoService.getById(agentId);
        if (agentInfo==null){
            return null;
        }else {
            switch (i){
                case 1:
                userRela.setBrandZoneAgentid(agentInfo.getAgentId());
                break;
                case 2:
                userRela.setAreaZoneAgentid(agentInfo.getAgentId());
                break;
                case 3:
                userRela.setChinaZoneAgentid(agentInfo.getAgentId());
                return null;
            }
            this.getByAgentId(agentInfo.getAgentId(),userRela,i+1);
            return agentInfo;
        }
    }

    public MobileUserInfo getJustUserInfo(String userId){
        String userJson =RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+userId);
        MobileUserInfo mobileUserInfo = new MobileUserInfo();
        if (!StringUtils.isEmpty(userJson)){
            mobileUserInfo = JsonUtils.toBean(userJson,MobileUserInfo.class);
        }else{
            mobileUserInfo = getByUserId(userId,false);
        }
        return mobileUserInfo;
    }

    public MobileUserInfo getById(String id){
        String userJson =RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+id);
        MobileUserInfo mobileUserInfo = new MobileUserInfo();
        if (!StringUtils.isEmpty(userJson)){
            mobileUserInfo = JsonUtils.toBean(userJson,MobileUserInfo.class);
            List<ServiceInfo> serviceInfoList = serviceInfoService.listServiceByPublisherId(id);
            mobileUserInfo.setServiceInfoList(serviceInfoList);
        }else{
            mobileUserInfo = getByUserId(id,true);
        }
        return mobileUserInfo;
    }

    public MobileUserInfo getByIdWithRela(String userId ,String targetUserId){
        String userJson =RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+targetUserId);
        MobileUserInfo mobileUserInfo = new MobileUserInfo();
        if (!StringUtils.isEmpty(userJson)){
            mobileUserInfo = JsonUtils.toBean(userJson,MobileUserInfo.class);
            List<ServiceInfo> serviceInfoList = serviceInfoService.listServiceByPublisherId(targetUserId);
            mobileUserInfo.setServiceInfoList(serviceInfoList);
        }else{
            mobileUserInfo = getByUserId(targetUserId,true);
        }
        //获取是否收藏
        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setUserId(userId);
        userFavourite.setFavouriteUserId(targetUserId);
        List<UserFavourite> list = userFavouriteService.loadData(userFavourite);
        if (list.size()>0){
            mobileUserInfo.setIsFavourite(1);
        }else {
            mobileUserInfo.setIsFavourite(0);
        }
        return mobileUserInfo;
    }

    public MobileUserInfo update(MobileUserInfo mobileUserInfo){
        mobileUserInfoDao.update(mobileUserInfo);
        mobileUserInfo = mobileUserInfoDao.selectOne(mobileUserInfo);
        return mobileUserInfo;
    }

    public MobileUserInfo update(String phoneCode ){
        MobileUserInfo mobileUserInfo= new MobileUserInfo();
        mobileUserInfo.setMobile(phoneCode);
        mobileUserInfo = mobileUserInfoDao.loadData(mobileUserInfo).get(0);
        return mobileUserInfo;
    }

    public MobileUserInfo getByPhoneCode(String phoneCode){
        MobileUserInfo mobileUserInfo = new MobileUserInfo();
        mobileUserInfo.setMobile(phoneCode);
        mobileUserInfo = mobileUserInfoDao.getByMobile(mobileUserInfo);
        return mobileUserInfo;
    }

    public MobileUserInfo getByUserId(String userId,boolean isGetServices){
        MobileUserInfo mobileUserInfo = mobileUserInfoDao.getByUserId(userId);
        if (isGetServices){
            List<ServiceInfo> serviceInfoList = serviceInfoService.listServiceByPublisherId(userId);
            mobileUserInfo.setServiceInfoList(serviceInfoList);
        }
        return mobileUserInfo;
    }


    public static void main(String[] args) {
        RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+"c5ffbbaa23a04b29ada8eb1ff3d1d502");
        System.out.println();
    }

    public List<MobileUserInfo> getSubordinate(String userId){
        return mobileUserInfoDao.getSubordinate(userId);
    }


    public MobileUserInfo getALlSubordinate(String userId){
        MobileUserInfo mobileUserInfo = new MobileUserInfo();
        //一级会员
        List<MobileUserInfo> firstSubList = mobileUserInfoDao.getSubordinate(userId);
        mobileUserInfo.setFirstSubordinates(firstSubList);
        //二级
        List<MobileUserInfo> secondSubList = new ArrayList<MobileUserInfo>();
        for (MobileUserInfo userInfo :firstSubList){
            List<MobileUserInfo> list = mobileUserInfoDao.getSubordinate(userInfo.getUserId());
            secondSubList.addAll(list);
        }
        mobileUserInfo.setSecondSubordinates(secondSubList);
        //三级
        List<MobileUserInfo> thirdSubList = new ArrayList<MobileUserInfo>();
        for (MobileUserInfo userInfo : secondSubList){
            List<MobileUserInfo> list = mobileUserInfoDao.getSubordinate(userInfo.getUserId());
            thirdSubList.addAll(list);
        }
        mobileUserInfo.setThirdSubordinates(thirdSubList);
        return mobileUserInfo;
    }

    public MobileUserInfo getParent(String userId){
        return mobileUserInfoDao.getParent(userId);
    }

    public Page<MobileUserInfo> getFirstSub(String userId, Page<MobileUserInfo> page,String level){
        MobileUserInfo mobileUserInfo = new MobileUserInfo();
        mobileUserInfo.setParentUserId(userId);
        if (level.equals("1")){
            return mobileUserInfoDao.getFirstSub(mobileUserInfo,page);
        }else if (level.equals("2")){
            return mobileUserInfoDao.getSecondSub(mobileUserInfo,page);
        }else {
            return mobileUserInfoDao.getThridSub(mobileUserInfo,page);
        }
    }

}
