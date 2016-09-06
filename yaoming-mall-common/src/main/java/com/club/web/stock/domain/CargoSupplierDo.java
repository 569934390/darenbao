package com.club.web.stock.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.JsonUtil;
import com.club.web.stock.dao.base.po.CargoSupplier;
import com.club.web.stock.domain.repository.CargoSupplierRepository;
import com.club.web.stock.vo.CargoSupplierVo;
/**
* @Title: CargoSupplierDo.java 
* @Package com.club.web.stock.domain 
* @Description: TODO供应商DO
* @author 柳伟军   
* @date 2016年4月6日 上午11:30:30 
* @version V1.0
 */
@Configurable
public class CargoSupplierDo {

	private Logger logger = LoggerFactory.getLogger(CargoSupplierDo.class);

	@Autowired
	CargoSupplierRepository cargoSupplierRepository;

	private Long id;

	private String code;

	private String name;

	private String contacts;

	private String tel;

	private String addr;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts == null ? null : contacts.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr == null ? null : addr.trim();
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
		cargoSupplierRepository.insert(this);
	}

	public void update() {
		cargoSupplierRepository.update(this);
	}
}