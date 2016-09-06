/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.vo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.club.web.image.domain.ImageDo;
import com.club.web.image.domain.repository.DoRepository;

/**
 *@Title: ImageVo
 *@Description:
 *@Author:Administrator
 *@Since:2016年3月25日
 *@Version:1.1.0
 */
public class EvaluationImageVo {
private Logger logger = LoggerFactory.getLogger(EvaluationImageVo.class);
	
    private Long id;


    private String url;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

}
