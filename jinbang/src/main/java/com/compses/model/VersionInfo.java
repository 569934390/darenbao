package com.compses.model;



/**
 版本管理表 Date:2016-07-26 00:31:38
 **/
public class VersionInfo{

    /****/
    private String versionId;
    /**版本号**/
    private String versionCode;
    /**版本名称**/
    private String versionName;
    /**更新内容**/
    private String updateContent;
    /**url**/
    private String url;

    public String getVersionId(){
        return versionId;
    }
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }
    public String getVersionCode(){
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getVersionName(){
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    public String getUpdateContent(){
        return updateContent;
    }
    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
