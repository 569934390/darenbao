package com.compses.model;



/**
 用户关系表 Date:2016-08-02 15:06:20
 **/
public class UserRela{

    /**主键id**/
    private String relaId;
    /**用户id**/
    private String userId;
    /**一级分销会员**/
    private String firstDistributorPerson;
    /**二级分销会员**/
    private String secondDistributorPerson;
    /**三级分销会员**/
    private String thirdDistributorPerson;
    /**品牌运营官**/
    private String brandZoneAgentid;
    /**地区运营官**/
    private String areaZoneAgentid;
    /**中国区运营官**/
    private String chinaZoneAgentid;

    public String getRelaId(){
        return relaId;
    }
    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFirstDistributorPerson(){
        return firstDistributorPerson;
    }
    public void setFirstDistributorPerson(String firstDistributorPerson) {
        this.firstDistributorPerson = firstDistributorPerson;
    }
    public String getSecondDistributorPerson(){
        return secondDistributorPerson;
    }
    public void setSecondDistributorPerson(String secondDistributorPerson) {
        this.secondDistributorPerson = secondDistributorPerson;
    }
    public String getThirdDistributorPerson(){
        return thirdDistributorPerson;
    }
    public void setThirdDistributorPerson(String thirdDistributorPerson) {
        this.thirdDistributorPerson = thirdDistributorPerson;
    }
    public String getBrandZoneAgentid(){
        return brandZoneAgentid;
    }
    public void setBrandZoneAgentid(String brandZoneAgentid) {
        this.brandZoneAgentid = brandZoneAgentid;
    }
    public String getAreaZoneAgentid(){
        return areaZoneAgentid;
    }
    public void setAreaZoneAgentid(String areaZoneAgentid) {
        this.areaZoneAgentid = areaZoneAgentid;
    }
    public String getChinaZoneAgentid(){
        return chinaZoneAgentid;
    }
    public void setChinaZoneAgentid(String chinaZoneAgentid) {
        this.chinaZoneAgentid = chinaZoneAgentid;
    }
}
