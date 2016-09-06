package com.club.web.event.service;

import com.club.web.event.vo.EventActivityUserinfoVo;

public interface EventActivityUserinfoService {

	EventActivityUserinfoVo findByOpenId(String openid);

}
