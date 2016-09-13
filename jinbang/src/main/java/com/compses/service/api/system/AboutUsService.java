package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.system.IAboutUsDao;
import com.compses.model.AboutUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nini on 2016/3/19.
 */

@Service
@Transactional
public class AboutUsService {

    @Autowired
    private IBaseCommonDAO baseCommonDAO;

    @Autowired
    private IAboutUsDao aboutUsDao;


    public AboutUs selectOneByVersion(String versionId,String type){
        return aboutUsDao.selectOneByVersion(versionId,type);
    }
}
