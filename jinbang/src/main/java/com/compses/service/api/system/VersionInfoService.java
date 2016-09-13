package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.system.IVersionDao;
import com.compses.dto.Page;
import com.compses.model.VersionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nini on 2016/3/19.
 */

@Service
@Transactional
public class VersionInfoService {

    @Autowired
    private IBaseCommonDAO baseCommonDAO;

    @Autowired
    private IVersionDao versionDao;


    public VersionInfo getLastVersion(String type){
        return versionDao.getLastVersion(type);
    }

    public Page<VersionInfo> selectDtoList(VersionInfo versionInfo, Page<VersionInfo> page) {
        return versionDao.selectDtoList(versionInfo,page);
    }

}
