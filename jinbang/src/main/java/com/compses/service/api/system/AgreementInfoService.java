package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.system.IAgreementInfoDao;
import com.compses.model.AgreementInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nini on 2016/3/19.
 */

@Service
@Transactional
public class AgreementInfoService {

    @Autowired
    private IBaseCommonDAO baseCommonDAO;

    @Autowired
    private IAgreementInfoDao agreementInfoDao;


    public AgreementInfo selectOneByVersion(String versionId,String type){
        return agreementInfoDao.selectOneByVersion(versionId,type);
    }
}
