package com.compses.model;



/**
 帮帮收藏表 Date:2016-08-03 09:40:47
 **/
public class UserFavourite{

    /**主键id**/
    private String relaId;
    /**用户id**/
    private String userId;
    /**被收藏用户id**/
    private String favouriteUserId;
    /**备注**/
    private String remark;

    public String getRelaId(){
        return relaId;
    }
    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFavouriteUserId(){
        return favouriteUserId;
    }
    public void setFavouriteUserId(String favouriteUserId) {
        this.favouriteUserId = favouriteUserId;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
