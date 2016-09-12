package com.compses.model;

import java.util.Date;

public class SCity{

    private Integer cityid;
    private String cityname;
    private String zipcode;
    private Integer provinceid;
    private Date datecreated;
    private Date dateupdated;
    private String status;

    public Integer getCityid(){
        return cityid;
    }
    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }
    public String getCityname(){
        return cityname;
    }
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
    public String getZipcode(){
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public Integer getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
