/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.dao.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.GoodLabelsMapper;
import com.club.web.store.dao.base.po.GoodLabels;
import com.club.web.store.dao.extend.GoodLabelsExtendMapper;
import com.club.web.store.domain.GoodLabelsDo;
import com.club.web.store.domain.repository.GoodLabelsRepository;

/**
 *@Title:
 *@Description:
 *@Author:Administrator
 *@Since:2016年4月8日
 *@Version:1.1.0
 */
@Repository
public class GoodLabelsReposityImpl implements GoodLabelsRepository{
  
	@Autowired
	GoodLabelsExtendMapper goodLabelsMapper;
	/* (non-Javadoc)
	 * @see com.club.web.store.domain.repository.GoodLabelsRepository#addGoodLabels(com.club.web.store.domain.GoodLabelsDo)
	 */
	
	@Override
	/**
	 * 添加商品标签
	 */
	public void addGoodLabels(GoodLabelsDo goodLabelsDo) {
		// TODO Auto-generated method stub
		GoodLabels goodLabels = new GoodLabels();
		BeanUtils.copyProperties(goodLabelsDo, goodLabels);
		goodLabelsMapper.insert(goodLabels);
	}
	
	
	public void deleteByGoodId(long goodId){
		goodLabelsMapper.deleteByGoodId(goodId);
	}

}
