package com.club.web.store.dao.extend;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.GoodLabelsMapper;
import com.club.web.store.dao.base.po.GoodLabels;

public interface GoodLabelsExtendMapper extends GoodLabelsMapper{
      void	deleteByGoodId(@Param("goodId")long goodId);
}