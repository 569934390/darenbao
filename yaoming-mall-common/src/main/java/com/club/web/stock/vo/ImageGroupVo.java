package com.club.web.stock.vo;

public class ImageGroupVo {
	private long groupId;
	private ImageVo[] images;
	public String getGroupId() {
		return groupId+"";
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public ImageVo[] getImages() {
		return images;
	}
	public void setImages(ImageVo[] images) {
		this.images = images;
	}
	public long getGroupIdLong(){
		return this.groupId;
	}
}
