package com.club.web.common.db.dao.mapper;

import com.club.web.common.db.po.StaffT;
import com.club.web.common.vo.StaffTVO;

public interface IStaffTMapper {
  

    StaffT selectByPrimaryKey(Long staffId);

    int updateByPrimaryKeySelective(StaffT record);
    
    StaffTVO selectById (Long staffId);
    
}