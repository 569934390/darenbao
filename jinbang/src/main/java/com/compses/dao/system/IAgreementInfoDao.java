package com.compses.dao.system;


import com.compses.model.AgreementInfo;

/**
 * Created by nini on 2016/3/19.
 */
public interface IAgreementInfoDao {

    public AgreementInfo selectOneByVersion(String versionId, String type);
}
