package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.AdviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nini on 2016/3/19.
 */

@Service
@Transactional
public class AdviceInfoService {

    @Autowired
    public IBaseCommonDAO baseCommonDAO;


    public void addNewAdvice(AdviceInfo adviceInfo){
        baseCommonDAO.save(adviceInfo);
    }
}
