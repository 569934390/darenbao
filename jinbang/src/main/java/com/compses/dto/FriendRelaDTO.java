package com.compses.dto;

import com.compses.model.FriendRela;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class FriendRelaDTO extends FriendRela{
    //用户名称
    private String userName;
    //性别
    private int gender;
    //头像
    private String portrait;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
