package com.compses.model;

import java.util.Date;

public class SDistrict{

    private Integer districtid;
    private String districtname;
    private Integer cityid;
    private Date datecreated;
    private Date dateupdated;

    public Integer getDistrictid(){
        return districtid;
    }
    public void setDistrictid(Integer districtid) {
        this.districtid = districtid;
    }
    public String getDistrictname(){
        return districtname;
    }
    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }
    public Integer getCityid(){
        return cityid;
    }
    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }
    public Date getDatecreated(){
        return datecreated;
    }
    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }
    public Date getDateupdated(){
        return dateupdated;
    }
    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }
}
