package com.compses.model;



/**
 服务收藏列表 Date:2016-07-26 22:33:55
 **/
public class ServiceFavoritesInfo{

    /**主键**/
    private String favoritesId;
    /**用户id**/
    private String userId;
    /**服务id**/
    private String serviceId;

    public String getFavoritesId(){
        return favoritesId;
    }
    public void setFavoritesId(String favoritesId) {
        this.favoritesId = favoritesId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getServiceId(){
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
