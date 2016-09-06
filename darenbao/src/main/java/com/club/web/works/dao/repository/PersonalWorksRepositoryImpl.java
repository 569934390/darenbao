package com.club.web.works.dao.repository;

import com.club.web.common.domain.impl.BaseRepositoryImpl;
import com.club.web.works.domain.repository.PersonalWorksRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Repository;

/**
 * Created by lifei on 2016/9/4.
 */
@Repository
public class PersonalWorksRepositoryImpl extends BaseRepositoryImpl implements PersonalWorksRepository {
    public PersonalWorksRepositoryImpl() {
        super("personal_works".toUpperCase());
    }
}
