package com.club.web.message.vo;

import java.util.List;

public class NewsVo {
	
	private String id;
	
	private String storeId;

	private String storeName;

	private String storePic;

	private String clientId;

	private String clientName;
	
	private String clientPic;

	private Integer type;
	
	private Integer status;
	
	private List<NewsContentVo> contents;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientPic() {
		return clientPic;
	}

	public void setClientPic(String clientPic) {
		this.clientPic = clientPic;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<NewsContentVo> getContents() {
		return contents;
	}

	public void setContents(List<NewsContentVo> contents) {
		this.contents = contents;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStorePic() {
		return storePic;
	}

	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}
	
}