package com.club.web.message.dao.base.po;

import java.util.Date;

public class MessageContent {
    private Long id;

    private Long messageId;

    private String content;

    private Integer type;

    private Long senderId;

    private Date sendTime;

    private Integer status;

    private Integer buyStatus;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(Integer buyStatus) {
		this.buyStatus = buyStatus;
	}
}