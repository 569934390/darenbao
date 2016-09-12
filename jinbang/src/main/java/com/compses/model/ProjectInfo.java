package com.compses.model;

import java.util.Date;

/**
 项目管理 Date:2016-04-10 21:05:50
 **/
public class ProjectInfo{

    /**主键**/
    private Integer projectId;
    /**项目类型**/
    private String type;
    /**项目名称**/
    private String projectName;
    /**项目简介**/
    private String descField;
    /**状态**/
    private String state;
    /**修改时间**/
    private Date modifyTime;
    /**创建日期**/
    private Date createDate;
    /**内容**/
    private String context;
    /**附件**/
    private String files;

    public Integer getProjectId(){
        return projectId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    public String getType(){
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getProjectName(){
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getDescField(){
        return descField;
    }
    public void setDescField(String descField) {
        this.descField = descField;
    }
    public String getState(){
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Date getModifyTime(){
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Date getCreateDate(){
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getContext(){
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getFiles(){
        return files;
    }
    public void setFiles(String files) {
        this.files = files;
    }
}
