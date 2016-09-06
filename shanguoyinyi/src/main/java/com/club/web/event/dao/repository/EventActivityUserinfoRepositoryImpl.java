package com.club.web.event.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.event.dao.base.po.EventActivityUserinfo;
import com.club.web.event.dao.extend.EventActivityUserinfoExtendMapper;
import com.club.web.event.domain.EventActivityUserinfoDo;
import com.club.web.event.domain.repository.EventActivityUserinfoRepository;
import com.club.web.event.vo.EventActivityUserinfoVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;

@Repository
public class EventActivityUserinfoRepositoryImpl implements EventActivityUserinfoRepository{
	
	@Autowired
	EventActivityUserinfoExtendMapper eventActivityUserinfoExtendMapper;
	
	@Override
	public EventActivityUserinfoDo create(WeixinUserInfoVo weixinUserinfoVo) {
		if(weixinUserinfoVo==null){
			return null;
		}
		EventActivityUserinfoDo eventActivityUserinfoDo=new EventActivityUserinfoDo();
		BeanUtils.copyProperties(weixinUserinfoVo, eventActivityUserinfoDo);
		return eventActivityUserinfoDo;
	}
	private EventActivityUserinfo getPoByDo(EventActivityUserinfoDo eventActivityUserinfoDo) {
		if(eventActivityUserinfoDo==null){
			return null;
		}
		EventActivityUserinfo eventActivityUserinfo=new EventActivityUserinfo();
		BeanUtils.copyProperties(eventActivityUserinfoDo, eventActivityUserinfo);
		return eventActivityUserinfo;
	}

	@Override
	public void update(EventActivityUserinfoDo eventActivityUserinfoDo) {
		eventActivityUserinfoExtendMapper.updateByPrimaryKey(getPoByDo(eventActivityUserinfoDo));
	}


	@Override
	public void insert(EventActivityUserinfoDo eventActivityUserinfoDo) {
		eventActivityUserinfoExtendMapper.insert(getPoByDo(eventActivityUserinfoDo));
	}

	@Override
	public EventActivityUserinfoVo findByOpenId(String openid) {
		return eventActivityUserinfoExtendMapper.findByOpenId(openid);
	}
	@Override
	public void delete(EventActivityUserinfoDo eventActivityUserinfoDo) {
		eventActivityUserinfoExtendMapper.deleteByPrimaryKey(eventActivityUserinfoDo.getId());
	}

}
