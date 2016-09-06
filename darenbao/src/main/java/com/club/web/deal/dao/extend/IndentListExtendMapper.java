package com.club.web.deal.dao.extend;

import java.util.List;

import com.club.web.deal.dao.base.IndentListMapper;
import com.club.web.deal.dao.base.po.IndentList;

public interface IndentListExtendMapper extends IndentListMapper{

	List<IndentList> findByIndentId(Long indentId);

	String findImgUrlByGoodSkuId(Long goodSkuId);

	String findSkuLongByGoodSkuId(Long goodSkuId);
}