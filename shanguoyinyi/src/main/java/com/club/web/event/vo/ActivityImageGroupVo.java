package com.club.web.event.vo;

/**
* @Title: ImageGroupVo.java 
* @Package com.club.web.event.vo 
* @Description: TODO(活动图片组) 
* @author 柳伟军   
* @date 2016年4月22日 下午3:02:56 
* @version V1.0
 */
public class ActivityImageGroupVo {
	private long groupId;
	private ActivityImageVo[] images;
	public String getGroupId() {
		return groupId+"";
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public ActivityImageVo[] getImages() {
		return images;
	}
	public void setImages(ActivityImageVo[] images) {
		this.images = images;
	}
	public long getGroupIdLong(){
		return this.groupId;
	}
}
