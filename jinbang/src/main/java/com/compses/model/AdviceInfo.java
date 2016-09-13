package com.compses.model;



/**
 意见反馈信息表 Date:2016-07-25 23:16:27
 **/
public class AdviceInfo{

    /****/
    private String adviceId;
    /**app类型**/
    private String appType;
    /**版本号**/
    private Integer version;
    /**反馈用户电话**/
    private String adverUserTel;
    /**反馈内容**/
    private String adverContent;

    private String adverUserName;

    public String getAdviceId(){
        return adviceId;
    }
    public void setAdviceId(String adviceId) {
        this.adviceId = adviceId;
    }
    public String getAppType(){
        return appType;
    }
    public void setAppType(String appType) {
        this.appType = appType;
    }
    public Integer getVersion(){
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getAdverUserTel(){
        return adverUserTel;
    }
    public void setAdverUserTel(String adverUserTel) {
        this.adverUserTel = adverUserTel;
    }
    public String getAdverContent(){
        return adverContent;
    }
    public void setAdverContent(String adverContent) {
        this.adverContent = adverContent;
    }

    public String getAdverUserName() {
        return adverUserName;
    }

    public void setAdverUserName(String adverUserName) {
        this.adverUserName = adverUserName;
    }
}

