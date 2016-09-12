package com.compses.service.api.system;

import com.compses.dao.system.ISDistrictDao;
import com.compses.model.SDistrict;
import com.compses.model.TBaseArea;
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
public class SDistrictService extends BaseCommonService{

    @Autowired
    private ISDistrictDao districtDao;

    public List<TBaseArea> selectAll(){
        TBaseArea sDistrict = new TBaseArea();
        return districtDao.loadData(sDistrict);
    }

    public List<TBaseArea> selectList(TBaseArea sDistrict){
       return districtDao.loadData(sDistrict);
    }

}
