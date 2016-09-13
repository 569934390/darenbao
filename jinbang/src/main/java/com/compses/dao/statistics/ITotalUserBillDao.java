package com.compses.dao.statistics;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.TotalUserBill;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public interface ITotalUserBillDao extends IBaseCommonDAO{

    TotalUserBill getByUserId(String userId);

}
