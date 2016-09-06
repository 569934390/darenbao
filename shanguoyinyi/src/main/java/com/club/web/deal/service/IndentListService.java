package com.club.web.deal.service;

import java.util.List;

import com.club.web.deal.vo.IndentListVo;

/**
 * 订单条目服务接口
 * @author zhuzd
 *
 */
public interface IndentListService {

	List<IndentListVo> getVoListByIndentId(Long indentId);

	boolean fillIndentListValue(String shopId,String id, IndentListVo indentListVo) throws Exception;

	void addAll(List<IndentListVo> indentListResult);

	void add(IndentListVo indentListVo);

}
