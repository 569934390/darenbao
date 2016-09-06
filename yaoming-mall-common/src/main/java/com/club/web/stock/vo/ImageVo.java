package com.club.web.stock.vo;

public class ImageVo {
	private long id;
	private String url;
	public String getId() {
		return id+"";
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getIdLong(){
		return this.id;
	}
	
}
