package com.club.web.message.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.message.domain.repository.MessageContentRepository;

/**
 * 信息内容表
 * @author zhuzd
 *
 */
@Configurable
public class MessageContentDo {
    
	private Long id;
	
	private Long messageId;

    private String content;
    
    private Integer type;
    
    private Long senderId;

    private Date sendTime;

    private Integer status;
    
    @Autowired
    private MessageContentRepository repository;
    
    public void newsAdd(){
    	repository.newsAdd(this);
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	
}