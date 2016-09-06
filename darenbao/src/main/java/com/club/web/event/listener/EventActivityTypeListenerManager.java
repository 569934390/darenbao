package com.club.web.event.listener;

import org.springframework.stereotype.Component;

import com.club.web.util.listener.BaseListenerMgr;

@Component
public class EventActivityTypeListenerManager extends BaseListenerMgr<IEventActivityTypeListener> implements IEventActivityTypeListener {

	public EventActivityTypeListenerManager() {
		super(IEventActivityTypeListener.class);
	}

	@Override
	public boolean delete(String[] ids) {
		boolean flag = true;
		for (IEventActivityTypeListener clazz : getClasses())
			if(!(flag = clazz.delete(ids)))
				break;
		return flag;
	}

}
