package com.club.web.works.domain;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.domain.IBaseDo;
import com.club.web.util.IdGenerator;
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
public class PersonalWorksDo extends PersonalWorksVo implements IBaseDo{

    @Autowired
    private PersonalWorksRepository personalWorksRepository;

    public Page<PersonalWorksVo> selectPageList() throws BaseAppException {
        return personalWorksRepository.selectPageList(this,PersonalWorksVo.class);
    }

    public List<PersonalWorksVo> selectList() throws BaseAppException{
        return personalWorksRepository.selectList(this, PersonalWorksVo.class);
    }

    @Override
    public PersonalWorksVo selectOne() throws BaseAppException {
        return personalWorksRepository.selectOne(this,PersonalWorksVo.class);
    }
    @Override
    public int insert() throws BaseAppException {
        this.setWorksId(IdGenerator.getDefault().nextId());
        return personalWorksRepository.insert(this);
    }
    @Override
    public int update() throws BaseAppException {
        return personalWorksRepository.update(this);
    }
    @Override
    public int delete() throws BaseAppException {
        return personalWorksRepository.delete(this.getWorksId());
    }

    @Override
    public int delete(long id) throws BaseAppException {
        return 0;
    }

    @Override
    public int delete(String id) throws BaseAppException {
        return 0;
    }
}
