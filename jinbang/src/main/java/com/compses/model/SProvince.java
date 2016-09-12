package com.compses.model;

import java.util.Date;

public class SProvince {

    private Integer provinceid;
    private String provincename;
    private Date datecreated;
    private Date dateupdated;

    public Integer getProvinceid(){
        return provinceid;
    }
    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }
    public String getProvincename(){
        return provincename;
    }
    public void setProvincename(String provincename) {
        this.provincename = provincename;
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
