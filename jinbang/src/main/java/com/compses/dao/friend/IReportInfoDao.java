package com.compses.dao.friend;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.ReportInfo;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public interface IReportInfoDao extends IBaseCommonDAO {

    Page<ReportInfo> listPageForReport(String userId, Page<ReportInfo> page);

}
