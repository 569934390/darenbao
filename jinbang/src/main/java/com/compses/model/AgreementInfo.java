package com.compses.model;

import java.util.Date;

/**
 电子协议信息表 Date:2016-07-25 23:46:37
 **/
public class AgreementInfo{

    /****/
    private String agereementId;
    /**发布者**/
    private String releaseUser;
    /**发布时间**/
    private Date releaseDate;
    /**版本号**/
    private Integer versionId;
    /****/
    private String type;
    /**协议内容**/
    private String content;

    public String getAgereementId(){
        return agereementId;
    }
    public void setAgereementId(String agereementId) {
        this.agereementId = agereementId;
    }
    public String getReleaseUser(){
        return releaseUser;
    }
    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser;
    }
    public Date getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    public Integer getVersionId(){
        return versionId;
    }
    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }
    public String getType(){
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
