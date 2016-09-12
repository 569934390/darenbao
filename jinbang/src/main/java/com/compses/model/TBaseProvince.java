package com.compses.model;



/**
 省份表 Date:2016-07-23 21:33:39
 **/
public class TBaseProvince{

//    /**省份ID**/
//    private int id;
    /**省份名**/
    private String provinceName;
    /**省份代码**/
    private String provinceCode;
    /**状态  0：停用，1启用，2删除 **/
//    private Integer provinceState;
//    /**创建时间**/
//    private String createTime;
//    /**备注**/
//    private String remark;

//    public int getId(){
//        return id;
//    }
//    public void setId(int id) {
//        this.id = id;
//    }
    public String getProvinceName(){
        return provinceName;
    }
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    public String getProvinceCode(){
        return provinceCode;
    }
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
//    public Integer getProvinceState(){
//        return provinceState;
//    }
//    public void setProvinceState(Integer provinceState) {
//        this.provinceState = provinceState;
//    }
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
