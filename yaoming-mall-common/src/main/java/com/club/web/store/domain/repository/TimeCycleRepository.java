package com.club.web.store.domain.repository;

import com.club.web.store.domain.TimeCycleDo;

public interface TimeCycleRepository {
	
	int insert(TimeCycleDo  timeCycleDo);
	
	int update(TimeCycleDo  timeCycleDo);
}
