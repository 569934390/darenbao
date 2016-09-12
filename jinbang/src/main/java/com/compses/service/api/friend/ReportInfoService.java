package com.compses.service.api.friend;

import com.compses.dao.friend.IReportInfoDao;
import com.compses.dto.Page;
import com.compses.model.ReportInfo;
import com.compses.service.common.BaseCommonService;
import com.compses.util.StringUtils;
import com.compses.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/8/2 0002.
 */

@Service
@Transactional
public class ReportInfoService extends BaseCommonService {

    @Autowired
    private IReportInfoDao reportInfoDao;


    public ReportInfo addReportInfo(ReportInfo reportInfo){
        reportInfo.setReporterId(UUIDUtils.getUUID());
        reportInfoDao.saveForUUid(reportInfo);
        return reportInfo;
    }

    public Page<ReportInfo> listPageForReport(String userId,Page<ReportInfo> page){
        return reportInfoDao.listPageForReport(userId,page);
    }
}
