package com.club.web.message.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.message.domain.repository.MessageRepository;

/**
 * 信息表
 * 
 * @author zhuzd
 *
 */
@Configurable
public class MessageDo {

	private Long id;

	private Long storeId;

	private Long clientId;

	private Integer type;

	private Integer status;
	
	@Autowired
	private MessageRepository repository;
	
	public void newsAdd(){
		repository.newsAdd(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
