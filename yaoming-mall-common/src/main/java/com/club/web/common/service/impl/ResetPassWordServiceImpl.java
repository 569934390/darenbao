package com.club.web.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.JsonUtil;
import com.club.web.common.db.dao.StaffTDao;
import com.club.web.common.db.po.StaffT;
import com.club.web.common.service.IResetPassWordService;
import com.club.web.common.vo.StaffTVO;
@Service
public class ResetPassWordServiceImpl implements IResetPassWordService{
@Autowired
StaffTDao staffTMapper;
	@Override
	public StaffTVO getStaff(long staffId) {
	
		return staffTMapper.selectById(staffId);
	}

	@Override
	public Map<String, Object> updateStaff(String modelJson,long staffId) {
		Map<String, Object> result=new HashMap<>();
		StaffT staffT = JsonUtil.toBean(modelJson, StaffT.class);
		
		if (staffT!=null) {
			StaffT staffTByKey=staffTMapper.selectByPrimaryKey(staffId);
			staffTByKey.setPassword(staffT.getPassword());
			staffTByKey.setLoginName(staffT.getLoginName());
			staffTMapper.updateByPrimaryKeySelective(staffTByKey);
			result.put("msg", "更新成功");
			result.put("success", true);
		}else{
			result.put("msg", "更新失败");
			result.put("success", false);
		}
		return result;
	}

}
