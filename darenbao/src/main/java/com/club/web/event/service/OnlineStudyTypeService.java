package com.club.web.event.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.club.core.common.Page;
import com.club.web.event.vo.EventOnlineStudyTypeVo;

public interface OnlineStudyTypeService {

	List<EventOnlineStudyTypeVo> getVoByParentId(String parentId);

	Page<EventOnlineStudyTypeVo> getParents();

	Map<String,Object> saveOrUpdateOnlineStudyType(EventOnlineStudyTypeVo eventOnlineStudyTypeVo,
			HttpServletRequest request);

	Map<String,Object> updateStatus(String action, String ids, HttpServletRequest request);

	Map<String,Object> delete(String ids);

	List<EventOnlineStudyTypeVo> getParentList();

	List<EventOnlineStudyTypeVo> getListByParentId(String parentId);


}
