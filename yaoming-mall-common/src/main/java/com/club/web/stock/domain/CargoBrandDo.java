package com.club.web.stock.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.stock.dao.base.po.CargoBrand;
import com.club.web.stock.dao.base.po.CargoSupplier;
import com.club.web.stock.domain.repository.CargoBrandRepository;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.stock.vo.CargoSupplierVo;
/**
* @Title: CargoBrandDo.java 
* @Package com.club.web.stock.domain 
* @Description: TODO 品牌DO
* @author 柳伟军   
* @date 2016年4月6日 上午11:30:11 
* @version V1.0
 */
@Configurable
public class CargoBrandDo {

	private Logger logger = LoggerFactory.getLogger(CargoBrandDo.class);

	@Autowired
	CargoBrandRepository cargoBrandRepository;

	private Long id;

	private String logo;

	private String name;

	private String url;

	private String supplierName;

	private Integer brandRecommendation;

	private Date createTime;

	private Long createBy;

	private Date updateTime;

	private Long updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo == null ? null : logo.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName == null ? null : supplierName.trim();
	}

	public Integer getBrandRecommendation() {
		return brandRecommendation;
	}

	public void setBrandRecommendation(Integer brandRecommendation) {
		this.brandRecommendation = brandRecommendation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public void insert() {
		cargoBrandRepository.insert(this);
	}

	public void update() {
		cargoBrandRepository.update(this);
	}

	public void delete() {
		cargoBrandRepository.deleteByPrimaryKey(this.getId());
	}
}