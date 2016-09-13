package com.compses.service.api.provider;

import com.compses.constants.ServiceConstants;
import com.compses.dao.provider.IServiceInfoDao;
import com.compses.dto.Page;
import com.compses.dto.UserInfo;
import com.compses.model.MobileUserInfo;
import com.compses.model.ServiceInfo;
import com.compses.model.ServiceInfoDTO;
import com.compses.model.TBaseCity;
import com.compses.redis.util.RedisGeoUtil;
import com.compses.redis.util.RedisHashSetUtil;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.service.api.base.MobileUserInfoService;
import com.compses.service.api.system.SCityService;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.GeoCoordinate;

import java.util.*;

/**
 * Created by Administrator on 2016/7/25 0025.
 */

@Service
@Transactional
public class ServiceInfoService extends BaseCommonService{

    @Autowired
    private IServiceInfoDao serviceInfoDao;
    @Autowired
    private MobileUserInfoService mobileUserInfoService;
    @Autowired
    private SCityService sCityService;


    public ServiceInfo addOrUpdateService(ServiceInfo serviceInfo){
        if (StringUtils.isEmpty(serviceInfo.getServiceId())){
            String id = UUIDUtils.getUUID();
            serviceInfo.setServiceId(id);
            serviceInfo.setShelfStatus(ServiceConstants.SERVICE_SHELFSTATUS_UP);
            TBaseCity city = sCityService.getByName(serviceInfo.getServiceCityName());
            serviceInfo.setServiceCityId(city.getCityCode());
            serviceInfo.setBuyNum(0);
            serviceInfo.setFavouriteNum(0);
            //获取用户信息并存储
            MobileUserInfo mobileUserInfo = mobileUserInfoService.getById(serviceInfo.getPublisherId());
            serviceInfo.setPublisherMobile(mobileUserInfo.getMobile());
            serviceInfo.setPublisherName(mobileUserInfo.getRealName());
            serviceInfo.setPublishDate(new Date());
            serviceInfoDao.saveForUUid(serviceInfo);
            String servcieJson = JsonUtils.toJson(serviceInfo);
            RedisHashSetUtil.hset(serviceInfo.getPublisherId(),serviceInfo.getServiceCategoryName(),servcieJson);

        }else {
            serviceInfo.setPublishDate(new Date());
            serviceInfoDao.update(serviceInfo);
            String servcieJson = JsonUtils.toJson(serviceInfo);
            RedisHashSetUtil.hset(serviceInfo.getPublisherId(), serviceInfo.getServiceCategoryName(), servcieJson);
        }
        if (!StringUtils.isEmpty(serviceInfo.getServicePhoto())){
            List<String> photoList = new ArrayList<String>();
            for (String photo :serviceInfo.getServicePhoto().split(",")){
                if (!StringUtils.isEmpty(photo)){
                    photoList.add(photo);
                }
            }
            serviceInfo.setPhotoList(photoList);
        }
        return serviceInfo;
    }

    public Page<ServiceInfo> listServiceByPublisherId(String publisherId,Page<ServiceInfo> page){
        page = serviceInfoDao.listServiceByPublisherId(publisherId,page);
        List<ServiceInfo> serviceInfoList = page.getResult();
        for (ServiceInfo serviceInfo :serviceInfoList){
            if (!StringUtils.isEmpty(serviceInfo.getServicePhoto())){
                List<String> photoList = new ArrayList<String>();
                for (String photo :serviceInfo.getServicePhoto().split(",")){
                    if (!StringUtils.isEmpty(photo)){
                        photoList.add(photo);
                    }
                }
                serviceInfo.setPhotoList(photoList);
            }
        }
        return page;
    }

    public ServiceInfo update(ServiceInfo serviceInfo){
        serviceInfoDao.update(serviceInfo);
        return serviceInfo;
    }

    public ServiceInfo getServiceInfoById(String serviceId,String userId){
        ServiceInfo serviceInfo  = serviceInfoDao.getServiceInfoById(serviceId,userId);
        String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+serviceInfo.getPublisherId());
        UserInfo userInfo = JsonUtils.toBean(userJson, UserInfo.class);
        GeoCoordinate geoCoordinate = RedisGeoUtil.geoGet(serviceInfo.getPublisherId());
        if (geoCoordinate!=null){
            userInfo.setLat(geoCoordinate.getLatitude());
            userInfo.setLng(geoCoordinate.getLongitude());
        }
        serviceInfo.setUserInfo(userInfo);
        return serviceInfo;
    }


    public ServiceInfo getById(String serviceId){
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceId(serviceId);
        serviceInfo = serviceInfoDao.selectOne(serviceInfo);
        return serviceInfo;
    }

    public List<ServiceInfo> listServiceByPublisherId(String publisherId){
        List<ServiceInfo> serviceInfoList = serviceInfoDao.listServiceByPublisherId(publisherId);
        for (ServiceInfo serviceInfo : serviceInfoList){
            if (!StringUtils.isEmpty(serviceInfo.getServicePhoto())){
                List<String> photoList = new ArrayList<String>();
                for (String photo :serviceInfo.getServicePhoto().split(",")){
                    if (!StringUtils.isEmpty(photo)){
                        photoList.add(photo);
                    }
                }
                serviceInfo.setPhotoList(photoList);
            }
        }
        return serviceInfoList;
    }


    public int removeServiceInfo(ServiceInfo serviceInfo){
        serviceInfo.setShelfStatus(ServiceConstants.SERVICE_SHELFSTATUS_DELETE);
        int length =serviceInfoDao.update(serviceInfo);
//        int length = serviceInfoDao.delete(serviceInfo);
        RedisHashSetUtil.hdel(serviceInfo.getPublisherId(),serviceInfo.getServiceCategoryName());
        return length;
    }

    /**
     * 更新服务状态
     * @param serviceInfo
     * @return
     */
    public int updateServiceShelvesStatus(ServiceInfo serviceInfo){
        int length = serviceInfoDao.update(serviceInfo);
        String serviceJosn = RedisHashSetUtil.hmget(serviceInfo.getPublisherId(),serviceInfo.getServiceCategoryName()).get(0);
        ServiceInfo redisService = JsonUtils.toBean(serviceJosn,ServiceInfo.class);
        redisService.setShelfStatus(serviceInfo.getShelfStatus());
        RedisHashSetUtil.hset(serviceInfo.getPublisherId(),serviceInfo.getServiceCategoryName(),JsonUtils.toJson(redisService));
        return length;
    }


    public Page<ServiceInfoDTO> findLastServiceInfos(String userId,String cityName,String categoryNames, Page<ServiceInfoDTO> page){
        Page<ServiceInfoDTO> pages = serviceInfoDao.findLastServiceInfos(userId,cityName,categoryNames,page) ;
        return pages;
    }

    public ServiceInfo getByUserIdAndCategory(String userId,String categoryName){
        ServiceInfo serviceInfo = new ServiceInfo();
        List<String> serviceJsonList = new ArrayList<String>();
        if (categoryName.equals("不限") || StringUtils.isEmpty(categoryName)){
            serviceJsonList = RedisHashSetUtil.hvals(userId);
        }else {
            serviceJsonList =RedisHashSetUtil.hmget(userId,categoryName);
        }
        if (serviceJsonList!=null){
            String serviceJson = serviceJsonList.get(0);
            serviceInfo = JsonUtils.toBean(serviceJson,ServiceInfo.class);
        }
        return serviceInfo;
    }

    public static void main(String[] args) {
        String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+"999b9fe900da4b5ca26f99fd5685df20");
        System.out.println(userJson);

        List<String> serviceInfoList =RedisHashSetUtil.hvals("6b12659658734765a2ae15a746b87652");
        for (String serviceJson : serviceInfoList){
            System.out.println(serviceJson);
        }
    }


}
