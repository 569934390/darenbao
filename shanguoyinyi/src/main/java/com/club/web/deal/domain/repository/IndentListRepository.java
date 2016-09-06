package com.club.web.deal.domain.repository;

import java.util.List;

import com.club.web.deal.domain.IndentListDo;

/**
 * 订单条目仓库接口
 * @author zhuzd
 *
 */
public interface IndentListRepository {

	List<IndentListDo> getDoListByIndentId(Long indentId);

	int add(IndentListDo indentListDo);

	int modify(IndentListDo indentListDo);
	
	String findImgUrlByGoodSkuId(Long goodSkuId);
	
	String findSkuLongByGoodSkuId(Long goodSkuId);

	void delete(Long id);
}
