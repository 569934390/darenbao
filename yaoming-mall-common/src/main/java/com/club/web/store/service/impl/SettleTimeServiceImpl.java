package com.club.web.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.club.framework.util.StringUtils;
import com.club.web.store.dao.base.po.SettleTime;
import com.club.web.store.dao.extend.SettleTimeExtendMapper;
import com.club.web.store.domain.SettleTimeDo;
import com.club.web.store.service.SettleTimeService;
import com.club.web.store.vo.SettleTimeVo;
@Service("settleTimeService")
public class SettleTimeServiceImpl implements SettleTimeService{
    @Autowired
    SettleTimeExtendMapper settleTimeExtendMapper; 
	@Override
	public Map<String, Object> saveOrUpdateSettleTime(SettleTimeVo settleTimeVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		SettleTimeDo settleTimeDo=new SettleTimeDo();
		try {
			settleTimeDo.setSettleDate(Integer.parseInt(settleTimeVo.getSettleDate()));
		} catch (Exception e) {
			Assert.isTrue(e.getMessage()==null, "排序号必须填数字");
		}
		settleTimeDo.setUpdateTime(settleTimeVo.getUpdateTime());
		if (StringUtils.isEmpty(settleTimeVo.getId())) {
			settleTimeDo.insert();
		}else{
			settleTimeDo.setId(Long.parseLong(settleTimeVo.getId()));
			settleTimeDo.update();}
		result.put("success", true);
		result.put("msg", "保存成功");
		return result;
	}

	@Override
	public SettleTimeVo getSettleTime() {
		SettleTimeVo settleTimeVo=new SettleTimeVo();
		List<SettleTime> settleTimeList=settleTimeExtendMapper.selectByPage();
		if (settleTimeList!=null&&settleTimeList.size()>0) {
			SettleTime settleTime=settleTimeList.get(0);
			settleTimeVo.setId(settleTime.getId()+"");
			settleTimeVo.setSettleDate(settleTime.getSettleDate()+"");
			settleTimeVo.setUpdateTime(settleTime.getUpdateTime());
		}
		return settleTimeVo;
	}

}
