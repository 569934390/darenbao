package com.compses.model;



/**
 系统设置 Date:2016-07-26 00:26:54
 **/
public class SystemConfig{

    /**id**/
    private String configId;
    /**设置项名称**/
    private String configName;
    /**设置项值**/
    private String configValue;
    /**文件url**/
    private String fileUrl;
    /**是否启用**/
    private String status;
    /**备注**/
    private String remark;
    /****/
    private String type;
    /****/
    private String configCode;

    public String getConfigId(){
        return configId;
    }
    public void setConfigId(String configId) {
        this.configId = configId;
    }
    public String getConfigName(){
        return configName;
    }
    public void setConfigName(String configName) {
        this.configName = configName;
    }
    public String getConfigValue(){
        return configValue;
    }
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    public String getFileUrl(){
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getType(){
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getConfigCode(){
        return configCode;
    }
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }
}
