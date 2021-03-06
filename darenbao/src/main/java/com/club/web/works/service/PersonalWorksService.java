package com.club.web.works.service;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.domain.IBaseDo;
import com.club.web.common.service.IBaseDoService;
import com.club.web.common.service.IBaseService;
import com.club.web.works.domain.PersonalWorksDo;
import com.club.web.works.vo.PersonalWorksVo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lifei on 2016/9/4.
 */
public interface PersonalWorksService extends IBaseDoService {
    public Page<PersonalWorksVo> selectPageList(PersonalWorksDo personalWorksDo) throws BaseAppException, InvocationTargetException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException;

    List<PersonalWorksVo> selectList(PersonalWorksDo personalWorksDo) throws NoSuchMethodException, BaseAppException, IllegalAccessException, InvocationTargetException;
}
