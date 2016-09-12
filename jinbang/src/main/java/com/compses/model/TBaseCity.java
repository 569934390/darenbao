package com.compses.model;



/**
 城市表 Date:2016-07-23 21:39:29
 **/
public class TBaseCity{

    /**城市ID**/
//    private int id;
    /**城市名**/
    private String cityName;
    /**城市代码**/
    private String cityCode;
    /**状态  0：停用，1启用，2删除 **/
//    private Integer cityState;
    /**省份ID**/
    private String provinceCode;
    //省份名称
    private String provinceName;
//    /**创建时间**/
//    private String createTime;
//    /**备注**/
//    private String remark;
//    /**别名**/
//    private String alias;


//    public int getId(){
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityCode(){
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
//    public Integer getCityState(){
//        return cityState;
//    }
//    public void setCityState(Integer cityState) {
//        this.cityState = cityState;
//    }
    public String getProvinceCode(){
        return provinceCode;
    }
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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
//    public String getAlias(){
//        return alias;
//    }
//    public void setAlias(String alias) {
//        this.alias = alias;
//    }
}
