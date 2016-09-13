package com.compses.service.api.base;

import com.compses.dao.base.ICashDetailsDao;
import com.compses.model.CashDetails;
import com.compses.service.common.BaseCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
@Service
@Transactional
public class CashDetailsService extends BaseCommonService{

    @Autowired
    private ICashDetailsDao cashDetailsDao;

    public List<CashDetails> listByConditions(String detailStatus){
        CashDetails search = new CashDetails();
        search.setDetailsStatus(detailStatus);
        List<CashDetails> list = cashDetailsDao.loadData(search);
        return list;
    }


}
