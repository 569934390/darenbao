package com.club.web.deal.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.deal.dao.base.IndentMapper;
import com.club.web.deal.vo.InvoiceVo;

/**
 * 发票Dao扩展类
 * 
 * @author zhuzd
 *
 */
public interface InvoiceExtendMapper extends IndentMapper {

	int queryTotalByMap(Map<String, Object> con);

	List<InvoiceVo> queryListByMap(Map<String, Object> con);
}