package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.ThemeManagerRepository;

@Configurable
public class ShopThemeManagerDo {
	@Autowired
	ThemeManagerRepository repository;
	private Long id;

	private String title;

	private String classifyId;

	private Integer status;

	private Integer sort;

	private String remk;

	private String titlePicUrl;

	private String subtitlePicUrlOne;

	private String subtitlePicUrlTwo;

	private Date createTime;
	/**
	 * 0-新增1-修改
	 */
	private int flag;
	/**
	 * 图片Id
	 */
	private String extendId;

	public Long getId() {
		return id;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getTitlePicUrl() {
		return titlePicUrl;
	}

	public void setTitlePicUrl(String titlePicUrl) {
		this.titlePicUrl = titlePicUrl == null ? null : titlePicUrl.trim();
	}

	public String getSubtitlePicUrlOne() {
		return subtitlePicUrlOne;
	}

	public void setSubtitlePicUrlOne(String subtitlePicUrlOne) {
		this.subtitlePicUrlOne = subtitlePicUrlOne == null ? null : subtitlePicUrlOne.trim();
	}

	public String getSubtitlePicUrlTwo() {
		return subtitlePicUrlTwo;
	}

	public void setSubtitlePicUrlTwo(String subtitlePicUrlTwo) {
		this.subtitlePicUrlTwo = subtitlePicUrlTwo == null ? null : subtitlePicUrlTwo.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void save() throws Exception {
		repository.save(this);
	}

	public void update() throws Exception {
		repository.update(this);
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}
}