package com.compses.dao.system;


import com.compses.model.AboutUs;

/**
 * Created by nini on 2016/3/19.
 */
public interface IAboutUsDao {

    public AboutUs selectOneByVersion(String versionId, String type);
}
