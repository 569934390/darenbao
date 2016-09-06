/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.BeanUtils;
import com.club.web.store.domain.GoodLabelsDo;
import com.club.web.store.domain.repository.GoodLabelsRepository;
import com.club.web.store.domain.repository.GoodsBaseLabelRepository;
import com.club.web.store.service.GoodLabelsService;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;

/**
 *@Title:
 *@Description:
 *@Author:Administrator
 *@Since:2016年4月8日
 *@Version:1.1.0
 */
@Transactional
@Service("goodLabelsServiceImpl")
public class GoodLabelsServiceImpl implements GoodLabelsService{

	/* (non-Javadoc)
	 * @see com.club.web.store.service.GoodLabelsService#addGoodLabels(com.club.web.store.vo.GoodLabelsVo)
	 */
	private Logger logger = LoggerFactory.getLogger(GoodLabelsServiceImpl.class);
	
	@Autowired
	private GoodLabelsRepository goodLabelsRepository;
	@Autowired
	private GoodsBaseLabelRepository goodsBaseLabelRepository;
	@Override
	public void addGoodLabels(GoodLabelsVo vo) {
		// TODO Auto-generated method stub
		GoodLabelsDo goodLabelsDo = new GoodLabelsDo ();
		BeanUtils.copyProperties(vo, goodLabelsDo);
		goodLabelsRepository.addGoodLabels(goodLabelsDo);
	}
	
	@Override
	public List<GoodsBaseLabelVo> selectGoodLabels(Long goodId) {
		// TODO Auto-generated method stub
		List<GoodsBaseLabelVo> list =null;
		list=goodsBaseLabelRepository.selectGoodsBaseLabelListByGoodId(goodId);
		return list;
	}
    
}
