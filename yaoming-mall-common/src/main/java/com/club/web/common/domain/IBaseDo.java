package com.club.web.common.domain;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.stock.domain.BaseDo;

import java.util.List;

/**
 * Created by lifei on 2016/9/8.
 */
public interface IBaseDo<T> {
    public Page<T> selectPageList() throws BaseAppException;
    public List<T> selectList() throws BaseAppException;
    public T selectOne() throws BaseAppException;
    public int insert() throws BaseAppException;
    public int update() throws BaseAppException;
    public int delete() throws BaseAppException;
    public int delete(long id) throws BaseAppException;
    public int delete(String id) throws BaseAppException;
}
