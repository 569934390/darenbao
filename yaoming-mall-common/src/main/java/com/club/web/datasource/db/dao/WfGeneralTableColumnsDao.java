package com.club.web.datasource.db.dao;


import com.club.core.common.Page;
import com.club.web.datasource.db.arg.WfGeneralTableColumnsArg;
import com.club.web.datasource.db.dao.mapper.IWfGeneralTableColumnsMapper;
import com.club.web.datasource.db.po.WfGeneralTableColumnsPO;
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
public class WfGeneralTableColumnsDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(WfGeneralTableColumnsArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(WfGeneralTableColumnsArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<WfGeneralTableColumnsPO> selectByArg(WfGeneralTableColumnsArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(WfGeneralTableColumnsPO record, WfGeneralTableColumnsArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(WfGeneralTableColumnsPO record, WfGeneralTableColumnsArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<WfGeneralTableColumnsPO> selectByArgAndPage(WfGeneralTableColumnsArg arg,
            Page<WfGeneralTableColumnsPO> resultPage) {
        List<WfGeneralTableColumnsPO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(WfGeneralTableColumnsPO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(WfGeneralTableColumnsPO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<WfGeneralTableColumnsPO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(Integer key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public WfGeneralTableColumnsPO selectByPrimaryKey(Integer key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(WfGeneralTableColumnsPO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WfGeneralTableColumnsPO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<WfGeneralTableColumnsPO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private WfGeneralTableColumnsArg buildQueryObject(Map<String, Object> params) {

        WfGeneralTableColumnsArg arg = new WfGeneralTableColumnsArg();
        WfGeneralTableColumnsArg.WfGeneralTableColumnsCriteria criteria = arg.createCriteria();

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

    public IWfGeneralTableColumnsMapper getMapper() {
    	return getSqlSession().getMapper(IWfGeneralTableColumnsMapper.class);
    }

}
