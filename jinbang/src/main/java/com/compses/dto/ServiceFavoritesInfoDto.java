package com.compses.dto;

import com.compses.model.ServiceFavoritesInfo;
import com.compses.model.ServiceInfo;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class ServiceFavoritesInfoDto extends ServiceFavoritesInfo{

    private ServiceInfo serviceInfo;

    private UserInfo userInfo;

    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
