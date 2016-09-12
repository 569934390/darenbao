package com.compses.model;



/**
 关于我们 Date:2016-07-25 22:31:43
 **/
public class AboutUs{

    /****/
    private String aboutUsId;
    /**内容**/
    private String commentInfo;
    /**版本号**/
    private Integer versionId;
    /**备注**/
    private String remark;
    /****/
    private String type;

    public String getAboutUsId(){
        return aboutUsId;
    }
    public void setAboutUsId(String aboutUsId) {
        this.aboutUsId = aboutUsId;
    }
    public String getCommentInfo(){
        return commentInfo;
    }
    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }
    public Integer getVersionId(){
        return versionId;
    }
    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
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
}
