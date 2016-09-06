package com.club.web.privilege.service.impl;

import com.club.core.db.dao.BaseDao;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.web.common.service.IBaseService;
import com.club.web.common.service.impl.BaseServiceImpl;
import com.club.web.privilege.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OperLogServiceImpl implements IOperLogService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private IBaseService baseService;

    @Override
    public void saveOperate(String bizIdStr, String state, String creater, String type, String context) throws BaseAppException {
        String[] bizIds = bizIdStr.split(",");
        for (String bizId : bizIds) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("operId", bizId);
            params.put("state", state==null?"00A":state);
            params.put("creater", creater);
            params.put("type", type);
            params.put("context", context);
            params.put("createDate", new Date());
            baseService.insert("oper_log", params);
        }
    }
}
