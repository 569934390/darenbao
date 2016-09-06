package com.club.web.store.dao.base.po;

import java.util.Date;

public class SubbranchGoodSoldout {
    private Long id;

    private Long goodId;

    private Long subranchId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getSubranchId() {
        return subranchId;
    }

    public void setSubranchId(Long subranchId) {
        this.subranchId = subranchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}