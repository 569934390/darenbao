package com.club.web.store.vo;

public class ClassifyVO {
	private long classifyId;
	private String title;
	private String image;
	public ClassifyVO(long classifyId, String title, String image) {
		this.classifyId = classifyId;
		this.title = title;
		this.image = image;
	}
	public long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(long classifyId) {
		this.classifyId = classifyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
