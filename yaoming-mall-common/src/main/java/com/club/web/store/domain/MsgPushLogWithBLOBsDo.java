package com.club.web.store.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.MsgPushRepository;

@Configurable
public class MsgPushLogWithBLOBsDo extends MsgPushLogDo {
	@Autowired
	MsgPushRepository repository;
	private String msgTitle;

	private String msgContent;

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle == null ? null : msgTitle.trim();
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent == null ? null : msgContent.trim();
	}

	public void save() throws Exception {
		repository.save(this);
	}
}