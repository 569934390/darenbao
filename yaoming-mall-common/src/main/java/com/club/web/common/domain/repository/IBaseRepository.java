package com.club.web.common.domain.repository;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.vo.BaseVo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by lifei on 2016/9/4.
 */
public interface IBaseRepository {
    public <T> Page<T>  selectPageList(BaseVo paramsBean,Class<T> clazz) throws BaseAppException;
    public <T> Page<T>  selectPageList(Map<String,Object> paramsMap,Class<T> clazz) throws BaseAppException;
    public <T> List<T>              selectList(BaseVo paramsBean,Class<T> clazz) throws BaseAppException;
    public List<Map<String,Object>> selectList(Map<String, Object> paramsMap) throws BaseAppException;
    public <T> T  selectOne(BaseVo record,Class<T> clazz) throws BaseAppException;
    public Map<String,Object>  selectOne(Map<String,Object> paramsMap) throws BaseAppException;
    public <T> int  insert(T record) throws BaseAppException;
    public <T> int  update(T record) throws BaseAppException;
    public <T> int  delete(T record) throws BaseAppException;
    public int  delete(String id) throws BaseAppException;

}
