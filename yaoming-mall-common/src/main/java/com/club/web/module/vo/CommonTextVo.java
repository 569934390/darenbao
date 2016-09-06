package com.club.web.module.vo;
/**
 * 通用文本Vo
 * @author zhuzd
 *
 */
public class CommonTextVo {
	
    private String id;

    private Integer type;

    private String fileUrl;

    private String content;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}