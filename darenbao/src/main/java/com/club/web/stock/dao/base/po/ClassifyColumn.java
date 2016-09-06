package com.club.web.stock.dao.base.po;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.dao.base.ClassifyColumnMapper;

@Configurable
public class ClassifyColumn {
    private Long id;

    private Long classifyId;

    private String imgUrl;

    private String name;

    private Integer orderIndex;
    
    @Autowired
    private ClassifyColumnMapper repository;
    
    public void add(){
		repository.insert(this);
    }
    
    public void modify(){
		repository.updateByPrimaryKeySelective(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}