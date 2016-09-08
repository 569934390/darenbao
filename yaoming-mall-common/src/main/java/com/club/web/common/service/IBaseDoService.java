package com.club.web.common.service;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.domain.IBaseDo;
import com.club.web.stock.domain.BaseDo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lifei on 2016/9/8.
 */
public interface IBaseDoService {
    public <T> Page<T> selectPageList(IBaseDo baseDo) throws BaseAppException;

    public <T> List<T> selectList(IBaseDo baseDo) throws BaseAppException;
    public <T> T selectOne(IBaseDo baseDo) throws BaseAppException;
    public int insert(IBaseDo baseDo) throws BaseAppException;
    public int update(IBaseDo baseDo) throws BaseAppException;
    public int delete(IBaseDo baseDo) throws BaseAppException;
}
