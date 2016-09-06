package com.club.web.common.domain.impl;

import com.club.core.common.Page;
import com.club.core.db.dao.BaseDao;
import com.club.core.spring.context.SpringApplicationContextHolder;
import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.ExceptionHandler;
import com.club.framework.exception.SystemErrorCode;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.DBUtils;
import com.club.framework.util.StringUtils;
import com.club.framework.util.Utils;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.domain.IBaseRepository;
import com.club.web.common.vo.BaseVo;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by lifei on 2016/9/4.
 */

public class BaseRepositoryImpl implements IBaseRepository {
    private String tableName;

    public BaseRepositoryImpl(String tableName) {
        this.tableName = tableName;
    }

    @Autowired
    private BaseDao baseDao;

    @Override
    public <T> Page<T> selectPageList(Page page,BaseVo paramsBean,Class<T> clazz) throws BaseAppException {
        Map<String,Object> paramsMap= null;
        try {
            paramsMap = BeanUtils.convertBeanNotNull(paramsBean);
            return selectPageList(paramsMap, page, clazz);
        } catch (IntrospectionException e) {
            ExceptionHandler.publish(SystemErrorCode.INVOKE_EXCEPTION, "转换类属性失败");
        } catch (IllegalAccessException e) {
            ExceptionHandler.publish(SystemErrorCode.INVOKE_EXCEPTION, "实例化JavaBean失败");
        } catch (InvocationTargetException e) {
            ExceptionHandler.publish(SystemErrorCode.INVOKE_EXCEPTION, "调用属性的setter方法失败");
        }
        return null;
    }
    @Override
    public <T> Page<T> selectPageList(Page page,Map<String,Object> paramsMap,Class<T> clazz) throws BaseAppException {
        return selectPageList(paramsMap, page, clazz);

    }

    @Override
    public <T> List<T> selectList(BaseVo paramsBean,Class<T> clazz) throws BaseAppException {
        try {
            Map<String,Object> paramsMap = BeanUtils.convertBeanNotNull(paramsBean);
            String sql=this.getSelectListSql(paramsMap);
            if(Utils.isEmpty(sql)){
                return null;
            }
            paramsMap.put("sql", sql);
            List<Map<String,Object>> list=StringUtils.toHump(baseDao.selectList(paramsMap));
            List<T> returnList=new ArrayList<>(list.size());
            int index=0;
            for (Map returnMap : list) {
                returnList.set(index, BeanUtils.copy(returnMap, clazz));
                index++;
            }
            return returnList;
        } catch (IntrospectionException e) {
            //如果分析类属性失败
            ExceptionHandler.publish(SystemErrorCode.INVOKE_EXCEPTION, "转换类属性失败");
        } catch (IllegalAccessException e) {
            //如果实例化 JavaBean 失败
            ExceptionHandler.publish(SystemErrorCode.INVOKE_EXCEPTION, "实例化JavaBean失败");
        } catch (InvocationTargetException e) {
            //如果调用属性的 setter 方法失败
            ExceptionHandler.publish(SystemErrorCode.INVOKE_EXCEPTION, "调用属性的setter方法失败");
        }
        return null;
    }

    @Override
    public List<Map<String,Object>> selectList(Map<String,Object> paramsMap) throws BaseAppException {
        String sql=this.getSelectListSql(paramsMap);
        if(Utils.isEmpty(sql)){
            return null;
        }
        paramsMap.put("sql", sql);
        return StringUtils.toHump(baseDao.selectList(paramsMap));

    }


    @Override
    public <T> int insert(T record) throws BaseAppException {
        Map<String,Object> keyValues = DBUtils.getInsertSql(record);
        keyValues = StringUtils.toHump(keyValues);
        return baseDao.insert(keyValues);
    }

    @Override
    public <T> int update(T record) throws BaseAppException {
        Map<String,Object> paramsMap = DBUtils.getUpdateSql(record);
        paramsMap = StringUtils.toHump(paramsMap);
        return baseDao.update(paramsMap);
    }

    @Override
    public <T> int delete(T record) throws BaseAppException {
        Map<String,Object> paramsMap = DBUtils.getDeleteSql(record);
        paramsMap = StringUtils.toHump(paramsMap);
        return baseDao.update(paramsMap);
    }

    @Override
    public int delete(String id) throws BaseAppException {
        DBMetaCache dbMetaCache= SpringApplicationContextHolder.getBean(DBMetaCache.class);
        String pkColumnName=dbMetaCache.getTable(tableName).getPks().get(0);
        String sql = DBUtils.getDeleteSql(tableName);
        Map<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("sql",sql);
        paramsMap.put(pkColumnName,id);
        paramsMap = StringUtils.toHump(paramsMap);
        return baseDao.update(paramsMap);
    }




    private String getSelectListSql(Map<String,Object> paramsMap) throws BaseAppException {
        String sql;
        if(paramsMap.get("selectColumns")==null||paramsMap.get("selectColumns").toString().isEmpty()){
            sql = DBUtils.getSelectListSql(tableName.toUpperCase(), paramsMap.keySet());

        }else{
            sql = DBUtils.getSelectListSql(tableName.toUpperCase(), paramsMap.keySet(),paramsMap.get("selectColumns").toString());

        }
        return sql;
    }
    private <T> Page<T> selectPageList(Map<String,Object> paramsMap,Page page,Class<T> clazz) throws BaseAppException {
        String sql=this.getSelectListSql(paramsMap);
        if(Utils.isEmpty(sql)){
            return null;
        }
        paramsMap.put("sql", DBUtils.getPageSql(sql, page));
        if(clazz==null){
            page.setResultList(StringUtils.toHump(baseDao.selectList(paramsMap)));
        }else{
            List<Map<String,Object>> list=StringUtils.toHump(baseDao.selectList(paramsMap));
            List<T> returnList=new ArrayList<>(list.size());
            int index=0;
            for (Map returnMap : list) {
                returnList.set(index, BeanUtils.copy(returnMap, clazz));
                index++;
            }
            page.setResultList(returnList);
        }

        paramsMap.put("sql", DBUtils.getCountSql(sql));
        Map<String,Object> r = baseDao.selectOne(paramsMap);
        page.setTotalRecords(Integer.parseInt(r.get("count").toString()));
        return page;
    }
}
