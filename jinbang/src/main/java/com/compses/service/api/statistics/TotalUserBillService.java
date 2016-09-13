package com.compses.service.api.statistics;

import com.compses.dao.statistics.ITotalUserBillDao;
import com.compses.model.TotalUserBill;
import com.compses.service.common.BaseCommonService;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
@Service
@Transactional
public class TotalUserBillService extends BaseCommonService{
    @Autowired
    private ITotalUserBillDao totalUserBillDao;

    public TotalUserBill save(TotalUserBill totalUserBill){
        totalUserBill.setTotalBillId(UUIDUtils.getUUID());
        totalUserBillDao.saveForUUid(totalUserBill);
        return totalUserBill;
    }

    public TotalUserBill getByUserId(String userId){
        TotalUserBill totalUserBill = totalUserBillDao.getByUserId(userId);
        return totalUserBill;
    }

    public void update(TotalUserBill totalUserBill){
        totalUserBillDao.update(totalUserBill);
    }

}
