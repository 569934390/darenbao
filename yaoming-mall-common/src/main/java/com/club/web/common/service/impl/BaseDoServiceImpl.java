package com.club.web.common.service.impl;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.domain.IBaseDo;
import com.club.web.common.service.IBaseDoService;

import java.util.List;

/**
 * Created by lifei on 2016/9/8.
 */
public class BaseDoServiceImpl implements IBaseDoService {
    @Override
    public <T> Page<T> selectPageList(IBaseDo baseDo) throws BaseAppException {
        return baseDo.selectPageList();
    }

    @Override
    public <T> List<T> selectList(IBaseDo baseDo) throws BaseAppException {
        return baseDo.selectList();
    }

    @Override
    public <T> T selectOne(IBaseDo baseDo) throws BaseAppException {
        return (T) baseDo.selectOne();
    }

    @Override
    public int insert(IBaseDo baseDo) throws BaseAppException {
        return baseDo.insert();
    }

    @Override
    public int update(IBaseDo baseDo) throws BaseAppException {
        return baseDo.update();
    }

    @Override
    public int delete(IBaseDo baseDo) throws BaseAppException {
        return baseDo.delete();
    }

}
