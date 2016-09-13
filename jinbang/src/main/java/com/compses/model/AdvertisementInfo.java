package com.compses.model;



/**
 广告管理表 Date:2016-07-25 22:33:53
 **/
public class AdvertisementInfo{

    /**id**/
    private String advertisementId;
    /**广告标题**/
    private String adverTitle;
    /**广告类型**/
    private String adverType;
    /**广告图片**/
    private String adverPhotos;
    /**是否启用**/
    private String adverStatus;
    /****/
    private String url;

    public String getAdvertisementId(){
        return advertisementId;
    }
    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }
    public String getAdverTitle(){
        return adverTitle;
    }
    public void setAdverTitle(String adverTitle) {
        this.adverTitle = adverTitle;
    }
    public String getAdverType(){
        return adverType;
    }
    public void setAdverType(String adverType) {
        this.adverType = adverType;
    }
    public String getAdverPhotos(){
        return adverPhotos;
    }
    public void setAdverPhotos(String adverPhotos) {
        this.adverPhotos = adverPhotos;
    }
    public String getAdverStatus(){
        return adverStatus;
    }
    public void setAdverStatus(String adverStatus) {
        this.adverStatus = adverStatus;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
