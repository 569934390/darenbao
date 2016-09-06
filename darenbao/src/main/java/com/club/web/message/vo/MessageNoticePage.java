package com.club.web.message.vo;

import java.util.Date;

/**
 * 信息通知类
 * 
 * @author zhuzd
 *
 */
public class MessageNoticePage {
	
	private String id;
	
	private Integer status;

	private Integer type;
	
	public String content;
	
	public Date contentTime;

	public Integer contentStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getContentTime() {
		return contentTime;
	}

	public void setContentTime(Date contentTime) {
		this.contentTime = contentTime;
	}

	public Integer getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(Integer contentStatus) {
		this.contentStatus = contentStatus;
	}
	
}