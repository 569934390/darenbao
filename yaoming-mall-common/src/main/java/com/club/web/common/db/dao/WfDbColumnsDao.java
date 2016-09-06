package com.club.web.common.db.dao;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.exception.SysRuntimeException;
import com.club.framework.util.StringUtils;

import com.club.web.common.db.arg.WfDbColumnsArg;
import com.club.web.common.db.arg.WfDbColumnsArg.WfDbColumnsCriteria;
import com.club.web.common.db.dao.mapper.IWfDbColumnsMapper;
import com.club.web.common.db.po.WfDbColumnsPO;

@Repository
public class WfDbColumnsDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(WfDbColumnsArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(WfDbColumnsArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<WfDbColumnsPO> selectByArg(WfDbColumnsArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(WfDbColumnsPO record, WfDbColumnsArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(WfDbColumnsPO record, WfDbColumnsArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<WfDbColumnsPO> selectByArgAndPage(WfDbColumnsArg arg,
            Page<WfDbColumnsPO> resultPage) {
        List<WfDbColumnsPO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(WfDbColumnsPO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(WfDbColumnsPO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<WfDbColumnsPO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(Integer key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public WfDbColumnsPO selectByPrimaryKey(Integer key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(WfDbColumnsPO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WfDbColumnsPO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<WfDbColumnsPO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private WfDbColumnsArg buildQueryObject(Map<String, Object> params) {

        WfDbColumnsArg arg = new WfDbColumnsArg();
        WfDbColumnsCriteria criteria = arg.createCriteria();

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

    public IWfDbColumnsMapper getMapper() {
    	return getSqlSession().getMapper(IWfDbColumnsMapper.class);
    }

}
