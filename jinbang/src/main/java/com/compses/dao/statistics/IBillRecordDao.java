package com.compses.dao.statistics;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.BillRecord;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public interface IBillRecordDao extends IBaseCommonDAO  {

    List<BillRecord> listAllRecord(BillRecord billRecord);
}
