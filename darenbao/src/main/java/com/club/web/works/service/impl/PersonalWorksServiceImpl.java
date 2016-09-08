package com.club.web.works.service.impl;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.util.BeanUtils;
import com.club.web.common.domain.IBaseDo;
import com.club.web.common.service.impl.BaseDoServiceImpl;
import com.club.web.common.vo.BaseVo;
import com.club.web.works.domain.PersonalWorksDo;
import com.club.web.works.service.PersonalWorksService;
import com.club.web.works.vo.PersonalWorksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by lifei on 2016/9/4.
 */
@Service
public class PersonalWorksServiceImpl extends BaseDoServiceImpl implements PersonalWorksService{
    private String selectColumns="works_id,works_title,works_cover,works_type";
    @Override
    public Page<PersonalWorksVo> selectPageList(PersonalWorksDo personalWorksDo) throws BaseAppException, InvocationTargetException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException {
        personalWorksDo.setSelectColumns(selectColumns);
        return personalWorksDo.selectPageList();
    }

    @Override
    public List<PersonalWorksVo> selectList(PersonalWorksDo personalWorksDo) throws NoSuchMethodException, BaseAppException, IllegalAccessException, InvocationTargetException {
        personalWorksDo.setSelectColumns(selectColumns);
        return personalWorksDo.selectList();
    }
}
