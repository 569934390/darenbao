package com.club.web.works.service;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.works.domain.PersonalWorksDo;
import com.club.web.works.vo.PersonalWorksVo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lifei on 2016/9/4.
 */
public interface PersonalWorksService {
    public Page<PersonalWorksDo> selectPageList(Page page, PersonalWorksVo personalWorksVo) throws BaseAppException, InvocationTargetException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<PersonalWorksVo> selectList(PersonalWorksVo personalWorksVo) throws NoSuchMethodException, BaseAppException, IllegalAccessException, InvocationTargetException;
}
