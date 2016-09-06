package com.club.web.event.domain.repository;

import com.club.web.event.domain.EventActivityUserinfoDo;
import com.club.web.event.vo.EventActivityUserinfoVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;

public interface EventActivityUserinfoRepository {

	void update(EventActivityUserinfoDo eventActivityUserinfoDo);

	EventActivityUserinfoDo create(WeixinUserInfoVo weixinUserinfoVo);

	void insert(EventActivityUserinfoDo eventActivityUserinfoDo);

	EventActivityUserinfoVo findByOpenId(String openid);

	void delete(EventActivityUserinfoDo eventActivityUserinfoDo);

}
