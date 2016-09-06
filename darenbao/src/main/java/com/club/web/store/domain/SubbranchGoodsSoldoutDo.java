package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.SubbranchRepository;
import com.club.web.util.IdGenerator;

@Configurable
public class SubbranchGoodsSoldoutDo {

	@Autowired
	SubbranchRepository subbranchRepository;

	private Long id;

	private Long goodId;

	private Long subranchId;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public Long getSubranchId() {
		return subranchId;
	}

	public void setSubranchId(Long subranchId) {
		this.subranchId = subranchId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int delete() {
		int result = 0;
		result = subbranchRepository.deleteSubbranchGoodsSoldout(this);
		return result;
	}

	public void save() {
		subbranchRepository.save(this);
	}

	public int insert() {
		int result;
		this.createTime = new Date();
		this.id = IdGenerator.getDefault().nextId();
		result = subbranchRepository.saveSubbranchGoodsSoldout(this);
		return result;
	}
}