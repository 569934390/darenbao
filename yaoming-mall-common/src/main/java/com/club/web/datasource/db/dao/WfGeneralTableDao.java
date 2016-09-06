package com.club.web.datasource.db.dao;


import com.club.core.common.Page;
import com.club.web.datasource.db.arg.WfGeneralTableArg;
import com.club.web.datasource.db.dao.mapper.IWfGeneralTableMapper;
import com.club.web.datasource.db.po.WfGeneralTablePO;
import com.club.framework.exception.SysRuntimeException;
import com.club.framework.util.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class WfGeneralTableDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(WfGeneralTableArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(WfGeneralTableArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<WfGeneralTablePO> selectByArg(WfGeneralTableArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(WfGeneralTablePO record, WfGeneralTableArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(WfGeneralTablePO record, WfGeneralTableArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<WfGeneralTablePO> selectByArgAndPage(WfGeneralTableArg arg,
            Page<WfGeneralTablePO> resultPage) {
        List<WfGeneralTablePO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(WfGeneralTablePO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(WfGeneralTablePO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<WfGeneralTablePO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(Integer key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public WfGeneralTablePO selectByPrimaryKey(Integer key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(WfGeneralTablePO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WfGeneralTablePO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<WfGeneralTablePO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private WfGeneralTableArg buildQueryObject(Map<String, Object> params) {

        WfGeneralTableArg arg = new WfGeneralTableArg();
        WfGeneralTableArg.WfGeneralTableCriteria criteria = arg.createCriteria();

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

    public IWfGeneralTableMapper getMapper() {
    	return getSqlSession().getMapper(IWfGeneralTableMapper.class);
    }

}
