package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.CarouselImgRepository;

/**
 * 轮播图片对象信息
 * 
 * @author wqh
 * 
 * @add by 2016-04-12
 */
@Configurable
public class CarouselImgDo {
	@Autowired
	CarouselImgRepository repository;
	private Long id;

	private Integer status;

	private Integer lineStatus;

	private Long category;

	private Integer sort;

	private String remk;

	private String picUrl;

	private Date updateTime;

	private Date createTime;

	private String richText;
	/**
	 * 0-存在(更新)1-不存在(新增)
	 */
	private int flag;
	/**
	 * 图片Id
	 */
	private String extendId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(Integer lineStatus) {
		this.lineStatus = lineStatus;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemk() {
		return remk;
	}

	public void setRemk(String remk) {
		this.remk = remk == null ? null : remk.trim();
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl == null ? null : picUrl.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText == null ? null : richText.trim();
	}

	public void save() throws Exception {
		repository.save(this);
	}

	public void update() throws Exception {
		repository.update(this);
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}
}