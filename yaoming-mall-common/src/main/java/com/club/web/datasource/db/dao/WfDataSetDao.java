package com.club.web.datasource.db.dao;

import com.club.core.common.Page;
import com.club.web.datasource.db.arg.WfDataSetArg;
import com.club.web.datasource.db.dao.mapper.IWfDataSetMapper;
import com.club.web.datasource.db.po.WfDataSetPO;
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
public class WfDataSetDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(WfDataSetArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(WfDataSetArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<WfDataSetPO> selectByArg(WfDataSetArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(WfDataSetPO record, WfDataSetArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(WfDataSetPO record, WfDataSetArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<WfDataSetPO> selectByArgAndPage(WfDataSetArg arg,
            Page<WfDataSetPO> resultPage) {
        List<WfDataSetPO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(WfDataSetPO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(WfDataSetPO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<WfDataSetPO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(Integer key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public WfDataSetPO selectByPrimaryKey(Integer key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(WfDataSetPO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WfDataSetPO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<WfDataSetPO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private WfDataSetArg buildQueryObject(Map<String, Object> params) {

        WfDataSetArg arg = new WfDataSetArg();
        WfDataSetArg.WfDataSetCriteria criteria = arg.createCriteria();

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

    public IWfDataSetMapper getMapper() {
    	return getSqlSession().getMapper(IWfDataSetMapper.class);
    }

}
