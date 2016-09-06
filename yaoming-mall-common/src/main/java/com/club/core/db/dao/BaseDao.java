/**
 * 
 */
package com.club.core.db.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.club.core.db.dao.mapper.IBaseMapper;
import com.club.framework.util.StringUtils;

@Repository("baseDao")
public class BaseDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
    
    public List<Map<String,Object>> selectList(String sqlKey,Map<String,Object> params){
    	List<Map<String,Object>> records = super.getSqlSession().selectList(sqlKey, params);
    	return StringUtils.toHump(records);
    }
    public List<Map<String,Object>> selectList(Map<String, Object> params){
        return getMapper().selectList(params);
    }

    public List<Map<String,Object>> selectSqlList(String sql,Map<String, Object> params){
        if (params==null){
            params = new HashMap<String, Object>();
        }
        params.put("sql",sql);
        return getMapper().selectList(params);
    }

    public IBaseMapper getMapper() {
        return getSqlSession().getMapper(IBaseMapper.class);
    }
    
    public Map<String,Object> selectOne(Map<String, Object> params){
    	return getMapper().selectOne(params);
    }

    public Map<String,Object> selectOne(String sql,Map<String, Object> params){
        if (params==null){
            params = new HashMap<String, Object>();
        }
        params.put("sql",sql);
        return getMapper().selectOne(params);
    }
    

    public int insert(Map<String, Object> params){
    	return getMapper().insert(params);
    }
    
    public int update(Map<String, Object> params){
    	return getMapper().update(params);
    }

    public int update(String sql,Map<String, Object> params){
        if (params==null){
            params = new HashMap<String, Object>();
        }
        params.put("sql",sql);
        return getMapper().update(params);
    }
    
    public int delete(Map<String, Object> params){
    	return getMapper().delete(params);
    }
}
