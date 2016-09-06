package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.store.dao.base.po.TimeCycle;
import com.club.web.store.dao.extend.TimeCycleExtendMapper;
import com.club.web.store.domain.TimeCycleDo;
import com.club.web.store.domain.repository.TimeCycleRepository;
@Repository
public class TimeCycleRepositoryImpl implements TimeCycleRepository{
	@Autowired
	private TimeCycleExtendMapper timeCycleDao;
	
	
	@Override
	public int insert(TimeCycleDo timeCycleDo) {
		return timeCycleDao.insert(getPoByDo(timeCycleDo));
	}

	@Override
	public int update(TimeCycleDo timeCycleDo) {
		return timeCycleDao.updateByPrimaryKeySelective(getPoByDo(timeCycleDo));
	}
	
	
	private TimeCycle getPoByDo(TimeCycleDo src) {
		TimeCycle target = null;
		if (src != null) {
			target = new TimeCycle();
			target.setId(src.getId());
			target.setType(src.getType());
			target.setUpdateTime(src.getUpdateTime());
			target.setDuration(src.getDuration());
		}
		return target;
	}
}
