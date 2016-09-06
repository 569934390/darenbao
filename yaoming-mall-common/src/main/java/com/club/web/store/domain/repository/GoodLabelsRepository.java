/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.domain.repository;

import com.club.web.store.domain.GoodLabelsDo;

/**
 *@Title:
 *@Description:
 *@Author:Administrator
 *@Since:2016年4月8日
 *@Version:1.1.0
 */
public interface GoodLabelsRepository {
	void addGoodLabels(GoodLabelsDo goodLabelsDo);
    void deleteByGoodId(long goodId);
}
