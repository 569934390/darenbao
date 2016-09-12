package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.SystemConfig;
import com.compses.service.common.BaseCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nini on 2016/4/4.
 */
@Service
@Transactional
public class SystemConfigService extends BaseCommonService {

    @Autowired
    private IBaseCommonDAO baseCommonDAO;


    /**
     * 获取所有可用的配置项
     * @return
     */
    public List<SystemConfig> listAllSystemconfig(String type){
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setStatus("00A");
        systemConfig.setType(type);
        List<SystemConfig> systemConfigList = this.loadData(systemConfig);
        return systemConfigList;
    }

    public SystemConfig update(SystemConfig systemConfig){
        baseCommonDAO.update(systemConfig);
        return systemConfig;
    }
}
