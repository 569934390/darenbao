package com.club.web.event.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventActivityTypeMapper;
import com.club.web.event.vo.EventActivityTypeVo;

public interface EventActivityTypeExtendMapper extends EventActivityTypeMapper{

	List<EventActivityTypeVo> queryActivityTypeVoByName(@Param("name")String name);

	List<Map<String, Object>> queryActivityTypePage(Map<String, Object> map);

	Long queryActivityTypeCountPage(Map<String, Object> map);

	List<EventActivityTypeVo> findListByStatus(@Param("status")Long status);
   
}