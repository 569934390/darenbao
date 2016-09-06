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

import com.club.web.common.db.arg.WfDbMetaArg;
import com.club.web.common.db.arg.WfDbMetaArg.WfDbMetaCriteria;
import com.club.web.common.db.dao.mapper.IWfDbMetaMapper;
import com.club.web.common.db.po.WfDbMetaPO;

@Repository
public class WfDbMetaDao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(WfDbMetaArg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(WfDbMetaArg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<WfDbMetaPO> selectByArg(WfDbMetaArg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(WfDbMetaPO record, WfDbMetaArg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(WfDbMetaPO record, WfDbMetaArg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<WfDbMetaPO> selectByArgAndPage(WfDbMetaArg arg,
            Page<WfDbMetaPO> resultPage) {
        List<WfDbMetaPO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(WfDbMetaPO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(WfDbMetaPO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<WfDbMetaPO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(Integer key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public WfDbMetaPO selectByPrimaryKey(Integer key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(WfDbMetaPO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(WfDbMetaPO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<WfDbMetaPO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private WfDbMetaArg buildQueryObject(Map<String, Object> params) {

        WfDbMetaArg arg = new WfDbMetaArg();
        WfDbMetaCriteria criteria = arg.createCriteria();

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

    public IWfDbMetaMapper getMapper() {
    	return getSqlSession().getMapper(IWfDbMetaMapper.class);
    }

}
