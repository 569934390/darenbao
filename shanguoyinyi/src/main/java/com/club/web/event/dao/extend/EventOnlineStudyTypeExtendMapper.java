package com.club.web.event.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventOnlineStudyTypeMapper;
import com.club.web.event.domain.EventOnlineStudyTypeDo;
import com.club.web.event.vo.EventOnlineStudyTypeVo;

public interface EventOnlineStudyTypeExtendMapper extends EventOnlineStudyTypeMapper{

	List<EventOnlineStudyTypeVo> getVoByEventOnlineStudyTypeVo(EventOnlineStudyTypeVo eventOnlineStudyTypeVo);

	List<EventOnlineStudyTypeVo> getEventOnlineStudyTypeVoByParentId(@Param("parentId")Long parendId);

	List<EventOnlineStudyTypeVo> getVoByName(@Param("name")String name);
   
}