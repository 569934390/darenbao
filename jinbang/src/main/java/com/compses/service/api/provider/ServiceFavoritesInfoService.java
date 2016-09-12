package com.compses.service.api.provider;

import com.compses.dao.provider.IServiceFavoritesInfoDao;
import com.compses.dto.Page;
import com.compses.dto.ServiceFavoritesInfoDto;
import com.compses.dto.UserInfo;
import com.compses.model.ServiceFavoritesInfo;
import com.compses.model.ServiceInfo;
import com.compses.redis.util.RedisHashSetUtil;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26 0026.
 */

@Transactional
@Service
public class ServiceFavoritesInfoService extends BaseCommonService{

    @Autowired
    private IServiceFavoritesInfoDao serviceFavoritesInfoDao;

    @Autowired
    private ServiceInfoService serviceInfoService;


    public void addOrDelFavoriteService(String userId,String serviceId){
        ServiceFavoritesInfo serviceFavoritesInfo = new ServiceFavoritesInfo();
        serviceFavoritesInfo.setUserId(userId);
        serviceFavoritesInfo.setServiceId(serviceId);
        List<ServiceFavoritesInfo> list = this.loadData(serviceFavoritesInfo);
        //获取服务对象内容
        ServiceInfo serviceInfo = serviceInfoService.getById(serviceId);
        //修改收藏关系
        if (list.size()==0){
            serviceFavoritesInfo.setFavoritesId(UUIDUtils.getUUID());
            serviceFavoritesInfoDao.saveForUUid(serviceFavoritesInfo);
            if (serviceInfo.getFavouriteNum()==null){
                serviceInfo.setFavouriteNum(0);
            }
            serviceInfo.setFavouriteNum(serviceInfo.getFavouriteNum() + 1);
        }else {
            serviceFavoritesInfoDao.deleteFavoriteService(serviceFavoritesInfo);
            serviceInfo.setFavouriteNum(serviceInfo.getFavouriteNum() - 1);
        }
        //service修改数据库
        serviceInfoService.update(serviceInfo);
        //service修改redis
        RedisHashSetUtil.hset(serviceInfo.getPublisherId(),serviceInfo.getServiceCategoryName(),JsonUtils.toJson(serviceInfo));
    }

    public Page<ServiceFavoritesInfoDto> listPage(String userId,Page<ServiceFavoritesInfoDto> page){
        page = serviceFavoritesInfoDao.listForPage(userId,page);
        for (ServiceFavoritesInfoDto serviceFavoritesInfoDto:page.getResult()){
            ServiceInfo serviceInfo = serviceInfoService.getServiceInfoById(serviceFavoritesInfoDto.getServiceId(), userId);
            serviceFavoritesInfoDto.setServiceInfo(serviceInfo);
            String userJson = RedisStringUtil.get(RedisKeyConfig.USER_PREFIX+serviceInfo.getPublisherId());
            UserInfo userInfo = JsonUtils.toBean(userJson,UserInfo.class);
            serviceFavoritesInfoDto.setUserInfo(userInfo);
        }
        return page;
    }





}
