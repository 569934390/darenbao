package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.impl.system.SCityDao;
import com.compses.dao.system.IsCityDao;
import com.compses.model.SCity;
import com.compses.model.TBaseCity;
import com.compses.service.common.BaseCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nini on 2016/4/9.
 */
@Service
@Transactional
public class SCityService extends BaseCommonService{

    @Autowired
    private IsCityDao CityDao;

    public List<TBaseCity> selectAll(){
        TBaseCity sCity = new TBaseCity();
        return this.loadData(sCity);
    }

    public List<TBaseCity> selectList(TBaseCity sCity){
        return this.loadData(sCity);
    }

    public TBaseCity getByCode(String cityCode){
        TBaseCity city = new TBaseCity();
        city.setCityCode(cityCode);
        city = CityDao.selectOne(city);
        return city;
    }

    public TBaseCity getByName(String cityName){
        TBaseCity city = CityDao.getByName(cityName);
        return city;
    }

}
