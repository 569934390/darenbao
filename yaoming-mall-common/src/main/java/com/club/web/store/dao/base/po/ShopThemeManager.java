package com.club.web.store.dao.base.po;

import java.util.Date;

public class ShopThemeManager {
    private Long id;

    private String title;

    private String classifyId;

    private Integer status;

    private Integer sort;

    private String remk;

    private String titlePicUrl;

    private String subtitlePicUrlOne;

    private String subtitlePicUrlTwo;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId == null ? null : classifyId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemk() {
        return remk;
    }

    public void setRemk(String remk) {
        this.remk = remk == null ? null : remk.trim();
    }

    public String getTitlePicUrl() {
        return titlePicUrl;
    }

    public void setTitlePicUrl(String titlePicUrl) {
        this.titlePicUrl = titlePicUrl == null ? null : titlePicUrl.trim();
    }

    public String getSubtitlePicUrlOne() {
        return subtitlePicUrlOne;
    }

    public void setSubtitlePicUrlOne(String subtitlePicUrlOne) {
        this.subtitlePicUrlOne = subtitlePicUrlOne == null ? null : subtitlePicUrlOne.trim();
    }

    public String getSubtitlePicUrlTwo() {
        return subtitlePicUrlTwo;
    }

    public void setSubtitlePicUrlTwo(String subtitlePicUrlTwo) {
        this.subtitlePicUrlTwo = subtitlePicUrlTwo == null ? null : subtitlePicUrlTwo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}