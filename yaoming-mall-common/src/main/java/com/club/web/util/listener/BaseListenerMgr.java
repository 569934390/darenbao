package com.club.web.util.listener;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@IgnoreListenerScaner
public abstract class BaseListenerMgr<T extends IBaseListener> {

	@Autowired private ListenerScaner scaner;
	
	protected Set<T> classes = new HashSet<>();
	
	private Class<T> type;
	
	public BaseListenerMgr(Class<T> type) {
		Assert.notNull(type, "类型不能为空");
		this.type = type;
	}

	@PostConstruct
	private void init() throws InstantiationException, IllegalAccessException{
		classes = scaner.getClassSet(type);
	}

	public Set<T> getClasses() {
		return classes;
	}
}
