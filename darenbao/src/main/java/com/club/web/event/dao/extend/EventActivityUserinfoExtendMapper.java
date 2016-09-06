package com.club.web.event.dao.extend;

import org.apache.ibatis.annotations.Param;

import com.club.web.event.dao.base.EventActivityUserinfoMapper;
import com.club.web.event.vo.EventActivityUserinfoVo;

public interface EventActivityUserinfoExtendMapper extends EventActivityUserinfoMapper{

	EventActivityUserinfoVo findByOpenId(@Param("openid")String openid);
    
}