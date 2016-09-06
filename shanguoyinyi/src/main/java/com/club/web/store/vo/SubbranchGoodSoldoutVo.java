package com.club.web.store.vo;

import java.util.Date;

public class SubbranchGoodSoldoutVo {
    private String id;

    private String goodId;

    private String subranchId;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getSubranchId() {
        return subranchId;
    }

    public void setSubranchId(String subranchId) {
        this.subranchId = subranchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}