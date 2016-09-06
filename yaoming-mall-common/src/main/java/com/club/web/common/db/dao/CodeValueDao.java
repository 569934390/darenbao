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
import com.club.web.common.db.po.CodeValuePO;

@Repository
public class CodeValueDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(CodeValueArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(CodeValueArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<CodeValuePO> selectByArg(CodeValueArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(CodeValuePO record, CodeValueArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(CodeValuePO record, CodeValueArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<CodeValuePO> selectByArgAndPage(CodeValueArg arg,
            Page<CodeValuePO> resultPage) {
        List<CodeValuePO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(CodeValuePO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(CodeValuePO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<CodeValuePO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(String key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public CodeValuePO selectByPrimaryKey(String key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(CodeValuePO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CodeValuePO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<CodeValuePO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private CodeValueArg buildQueryObject(Map<String, Object> params) {

        CodeValueArg arg = new CodeValueArg();
        CodeValueCriteria criteria = arg.createCriteria();

        Class criteriaClass = criteria.getClass();
        Set keys = params.keySet();

        if (keys != null) {

            Iterator iterator = keys.iterator();

            while (iterator.hasNext()) {

                Object key = iterator.next();
                Object value = params.get(key);
                for (Method method : criteriaClass.getMethods()) {
                    if (method.getName().equals(
                            "and"+ StringUtils.toUpperCaseFirstOne(key.toString()) + "EqualTo")) {
                        try {
                            method.invoke(criteria, value);
                        }
                        catch (Exception e) {
                            throw new SysRuntimeException(e);
                        }
                        break;
                    }
                }
            }
        }
        return arg;
    }

    public ICodeValueMapper getMapper() {
    	return getSqlSession().getMapper(ICodeValueMapper.class);
    }

}
