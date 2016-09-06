package com.club.web.event.dao.base.po;

import java.util.Date;

public class EventOnlineStudy {
    private Long id;

    private String title;

    private Long studyType;

    private Long studyChildType;

    private Long type;

    private Long readNum;

    private String author;

    private String videoUrl;

    private String covePic;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private String content;
    
    private String file;

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

    public Long getStudyType() {
        return studyType;
    }

    public void setStudyType(Long studyType) {
        this.studyType = studyType;
    }

    public Long getStudyChildType() {
        return studyChildType;
    }

    public void setStudyChildType(Long studyChildType) {
        this.studyChildType = studyChildType;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getCovePic() {
        return covePic;
    }

    public void setCovePic(String covePic) {
        this.covePic = covePic == null ? null : covePic.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
    
}