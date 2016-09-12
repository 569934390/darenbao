package com.compses.model;



/**
 区域表 Date:2016-07-23 21:50:00
 **/
public class TBaseArea{

    /**区域ID**/
//    private int id;
    /**区域名**/
    private String areaName;
    /**区域编码**/
    private String areaCode;
    /**状态  0：停用，1启用，2删除 **/
//    private Integer areaState;
    /**城市代码**/
    private String cityCode;
    /**创建时间**/
//    private String createTime;
//    /**备注**/
//    private String remark;

//    public int getId(){
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
    public String getAreaName(){
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getAreaCode(){
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
//    public Integer getAreaState(){
//        return areaState;
//    }
//    public void setAreaState(Integer areaState) {
//        this.areaState = areaState;
//    }
    public String getCityCode(){
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
//    public String getCreateTime(){
//        return createTime;
//    }
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
//    public String getRemark(){
//        return remark;
//    }
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
}
