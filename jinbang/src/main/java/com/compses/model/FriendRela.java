package com.compses.model;



/**
 好友列表 Date:2016-07-27 23:19:39
 **/
public class FriendRela{

    /**主键id**/
    private String friendId;
    /**发起者id**/
    private String userId;
    /**接受者id**/
    private String receiveId;
    /**接受状态**/
    private String status;
    /**提交信息**/
    private String info;

    public String getFriendId(){
        return friendId;
    }
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getReceiveId(){
        return receiveId;
    }
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
}
