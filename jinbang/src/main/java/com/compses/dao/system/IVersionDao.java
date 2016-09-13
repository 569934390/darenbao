package com.compses.dao.system;


import com.compses.dto.Page;
import com.compses.model.VersionInfo;

/**
 * Created by nini on 2016/3/19.
 */
public interface IVersionDao {

    public VersionInfo getLastVersion(String type);

    public VersionInfo getById(int id);

    public Page<VersionInfo> selectDtoList(VersionInfo versionInfo, Page<VersionInfo> page);

}
