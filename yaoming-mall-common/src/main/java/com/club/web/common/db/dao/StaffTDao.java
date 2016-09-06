package com.club.web.common.db.dao;

import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.exception.SysRuntimeException;
import com.club.framework.util.StringUtils;

import com.club.web.common.db.arg.CodeValueArg;
import com.club.web.common.db.arg.CodeValueArg.CodeValueCriteria;
import com.club.web.common.db.dao.mapper.ICodeValueMapper;
import com.club.web.common.db.dao.mapper.IStaffTMapper;
import com.club.web.common.db.po.CodeValuePO;
import com.club.web.common.db.po.StaffT;
import com.club.web.common.service.IResetPassWordService;
import com.club.web.common.vo.StaffTVO;

@Repository
public class StaffTDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
    public IStaffTMapper getMapper() {
    	return getSqlSession().getMapper(IStaffTMapper.class);
    }
    public  StaffT selectByPrimaryKey(Long staffId){
    	return getMapper().selectByPrimaryKey(staffId);
    }

    public int updateByPrimaryKeySelective(StaffT record){
    	return getMapper().updateByPrimaryKeySelective(record);
    }
    
    public StaffTVO selectById (Long staffId){
    	return getMapper().selectById(staffId);
    }
}
