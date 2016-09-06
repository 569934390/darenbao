package com.club.web.event.vo;

import java.util.Date;

import com.club.framework.util.DateUtils;
import com.club.web.util.DateParseUtil;

public class EventOnlineStudyVo {
    private String id;

    private String title;

    private String studyType;
    
    private String studyTypeName;

    private String studyChildType;
    
    private String studyChildTypeName;

    private Long type;

    private Long readNum;

    private String author;

    private String videoUrl;

    private String covePic;
    
    private String covePicUrl;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private String content;
    
    private String createTime1;
    
    private String file;
    

    public String getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getStudyType() {
		return studyType;
	}

	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}

	public String getStudyChildType() {
		return studyChildType;
	}

	public void setStudyChildType(String studyChildType) {
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

	public String getStudyTypeName() {
		return studyTypeName;
	}

	public void setStudyTypeName(String studyTypeName) {
		this.studyTypeName = studyTypeName;
	}

	public String getStudyChildTypeName() {
		return studyChildTypeName;
	}

	public void setStudyChildTypeName(String studyChildTypeName) {
		this.studyChildTypeName = studyChildTypeName;
	}

	public String getCovePicUrl() {
		return covePicUrl;
	}

	public void setCovePicUrl(String covePicUrl) {
		this.covePicUrl = covePicUrl;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}