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

import com.club.web.common.db.arg.WfDbTableArg;
import com.club.web.common.db.arg.WfDbTableArg.WfDbTableCriteria;
import com.club.web.common.db.dao.mapper.IWfDbTableMapper;
import com.club.web.common.db.po.WfDbTablePO;

@Repository
public class WfDbTableDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(WfDbTableArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(WfDbTableArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<WfDbTablePO> selectByArg(WfDbTableArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(WfDbTablePO record, WfDbTableArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(WfDbTablePO record, WfDbTableArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<WfDbTablePO> selectByArgAndPage(WfDbTableArg arg,
            Page<WfDbTablePO> resultPage) {
        List<WfDbTablePO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(WfDbTablePO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(WfDbTablePO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<WfDbTablePO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(Integer key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public WfDbTablePO selectByPrimaryKey(Integer key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(WfDbTablePO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WfDbTablePO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<WfDbTablePO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private WfDbTableArg buildQueryObject(Map<String, Object> params) {

        WfDbTableArg arg = new WfDbTableArg();
        WfDbTableCriteria criteria = arg.createCriteria();

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

    public IWfDbTableMapper getMapper() {
    	return getSqlSession().getMapper(IWfDbTableMapper.class);
    }

}
