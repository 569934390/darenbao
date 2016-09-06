package com.club.web.store.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.StringUtils;
import com.club.web.store.constant.TimeCycleType;
import com.club.web.store.dao.extend.TimeCycleExtendMapper;
import com.club.web.store.domain.TimeCycleDo;
import com.club.web.store.service.TimeCycleService;
import com.club.web.store.vo.TimeCycleVo;
import com.club.web.util.IdGenerator;

@Service
public class TimeCycleServiceImpl implements TimeCycleService{
    @Autowired
    private TimeCycleExtendMapper timeCycleDao; 

	@Override
	public Map<String, Object> saveOrUpdate(TimeCycleVo timeCycle) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(timeCycle.getDuration())){
			result.put("success", false);
			result.put("msg", "请输入数值！");
			return result;
		}if(!StringUtils.isNumeric(timeCycle.getDuration())){
			result.put("success", false);
			result.put("msg", "周期时长必须为数字！");
			return result;
		}
		timeCycle.setUpdateTime(new Date());
		if(StringUtils.isEmpty(timeCycle.getId())){
			timeCycle.setId(IdGenerator.getDefault().nextId()+"");
			getDoByVo(timeCycle).add();
		}else{
			getDoByVo(timeCycle).update();
		}	
		result.put("success", true);
		result.put("msg", timeCycle.getId());
		return result;
	}

	@Override
	public TimeCycleVo detail(int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		return timeCycleDao.findVoByMap(map);
	}

	@Override
	public TimeCycleVo getSettleTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", TimeCycleType.结算周期.getDbData());
		return timeCycleDao.findVoByMap(map);
	}

	@Override
	public TimeCycleVo getIndentShipTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", TimeCycleType.订单自动收货有效时间.getDbData());
		return timeCycleDao.findVoByMap(map);
	}
	
	private TimeCycleDo getDoByVo(TimeCycleVo src){
		TimeCycleDo target = null;
		if (src != null) {
			target = new TimeCycleDo();
			target.setId(src.getId() == null ? null : Long.valueOf(src.getId()));
			target.setType(src.getType());
			target.setUpdateTime(src.getUpdateTime());
			target.setDuration(src.getDuration() == null ? null : Integer.valueOf(src.getDuration()));
		}
		return target;
	}
}
