/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.service;

import java.util.List;

import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;

/**
 *@Title:
 *@Description:
 *@Author:Administrator
 *@Since:2016年4月8日
 *@Version:1.1.0
 */
public interface GoodLabelsService {
   void addGoodLabels(GoodLabelsVo vo);
   public List<GoodsBaseLabelVo> selectGoodLabels(Long goodId);
}
