package com.club.web.store.vo;


/**
 * 主题区对象
 * 
 * @add by 2016-05-03
 */
public class ShopThemeExtendVo {

	private String id;

	private String title;

	private String titlePicUrl;

	private String classifyId;

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

	public String getTitlePicUrl() {
		return titlePicUrl;
	}

	public void setTitlePicUrl(String titlePicUrl) {
		this.titlePicUrl = titlePicUrl == null ? null : titlePicUrl.trim();
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
}