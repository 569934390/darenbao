package com.compses.dao.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.TBaseCity;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public interface IsCityDao extends IBaseCommonDAO {

    TBaseCity getByName(String cityName);

}
