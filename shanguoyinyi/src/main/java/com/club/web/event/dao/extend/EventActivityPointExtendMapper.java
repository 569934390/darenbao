package com.club.web.event.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventActivityPointMapper;
import com.club.web.event.vo.EventActivityPointVo;

public interface EventActivityPointExtendMapper extends EventActivityPointMapper{

	EventActivityPointVo findEventActivityPointVoByUserInfoAndEventActivityId(@Param("eventActivityId")String eventActivityId, @Param("userId")Long userId);

	List<EventActivityPointVo> findByActivityId(@Param("eventActivityId")Long eventActivityId);
    
}