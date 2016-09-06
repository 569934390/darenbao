/**
 * 
 */
package com.club.web.common.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.db.dao.BaseDao;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.framework.log.ClubLogManager;
import com.club.web.common.service.IQuartzService;


@Service
public class QuartzServiceImpl implements IQuartzService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(QuartzServiceImpl.class);

    @Autowired
    private BaseDao baseDao;

    /**
     * 主键生成器
     */
    @Resource(name = "sequenceProcGenerator")
    private ISequenceGenerator sequenceGenerator;
    
    public void addTask(Object task){
    	logger.debug("add Task :{0}",task);
    }

}
