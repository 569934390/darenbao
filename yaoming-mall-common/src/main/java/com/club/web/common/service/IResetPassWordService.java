package com.club.web.common.service;

import java.util.Map;

import com.club.web.common.db.po.StaffT;
import com.club.web.common.vo.StaffTVO;

public interface IResetPassWordService {
 StaffTVO getStaff(long staffId);
 Map<String,Object> updateStaff(String modelJson,long staffId);
}
