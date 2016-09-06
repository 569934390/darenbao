package com.club.web.works.domain;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.works.domain.repository.PersonalWorksRepository;
import com.club.web.works.vo.PersonalWorksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lifei on 2016/9/4.
 */
@Configurable
public class PersonalWorksDo extends PersonalWorksVo{

    @Autowired
    private PersonalWorksRepository personalWorksRepository;
    public void insert() throws BaseAppException {
        personalWorksRepository.insert(this);
    }
    public Page<PersonalWorksDo> selectPageList(Page page,PersonalWorksVo personalWorksVo) throws BaseAppException {
        return personalWorksRepository.selectPageList(page,personalWorksVo,PersonalWorksDo.class);
    }

    public List<PersonalWorksVo> selectList(PersonalWorksVo personalWorksVo) throws BaseAppException{
        return personalWorksRepository.selectList(personalWorksVo, PersonalWorksVo.class);
    }
}
