package com.club.web.event.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.web.event.domain.repository.EventActivityUserinfoRepository;
import com.club.web.event.service.EventActivityUserinfoService;
import com.club.web.event.vo.EventActivityUserinfoVo;

/**
* @Title: EventActivityUserinfoServiceImpl.java 
* @Package com.club.web.event.service.impl 
* @Description: TODO(活动参与者) 
* @author 柳伟军   
* @date 2016年4月28日 上午11:22:23 
* @version V1.0
 */
@Service("eventActivityUserinfoService")
@Transactional
public class EventActivityUserinfoServiceImpl implements EventActivityUserinfoService{

	@Autowired 
	EventActivityUserinfoRepository eventActivityUserinfoRepository;
	
	@Override
	public EventActivityUserinfoVo findByOpenId(String openid) {
		return eventActivityUserinfoRepository.findByOpenId(openid);
	}

}
