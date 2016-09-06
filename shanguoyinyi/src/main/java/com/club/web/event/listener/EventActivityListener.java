package com.club.web.event.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.event.domain.repository.EventActivityRepository;
import com.club.web.event.vo.EventActivityDetailsVo;
import com.club.web.store.domain.TradeGoodSkuDo;

@Configurable
public class EventActivityListener implements IEventActivityTypeListener{

	@Autowired
	EventActivityRepository eventActivityRepository;
	
	public boolean delete(String[] ids) {
		
		Long count=eventActivityRepository.queryEventActivityByTypeIds(ids);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
}
