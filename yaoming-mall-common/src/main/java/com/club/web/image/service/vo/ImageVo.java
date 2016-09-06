/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.image.service.vo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.club.web.image.domain.ImageDo;
import com.club.web.image.domain.repository.DoRepository;

/**
 *@Title: ImageVo
 *@Description:
 *@Author:Administrator
 *@Since:2016年3月25日
 *@Version:1.1.0
 */
public class ImageVo {
private Logger logger = LoggerFactory.getLogger(ImageVo.class);
	
    private Long id;

    private Long groupid;

    private String picUrl;

    private Date createTime;

    private Long createBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}
