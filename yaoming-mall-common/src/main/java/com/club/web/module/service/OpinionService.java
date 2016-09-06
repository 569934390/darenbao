package com.club.web.module.service;

import java.util.Map;

import com.club.core.common.Page;
import com.club.web.module.vo.OpinionVo;
/**
 * 意见反馈服务接口
 * @author zhuzd
 *
 */
public interface OpinionService {

	Page<Map<String, Object>> list(Page<Map<String, Object>> page);

	boolean add(OpinionVo opinionVo);
	
	boolean weixinAdd(OpinionVo opinionVo);

	boolean deleteByIds(String idstr);

}
