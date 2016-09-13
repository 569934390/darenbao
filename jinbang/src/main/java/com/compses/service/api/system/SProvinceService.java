package com.compses.service.api.system;

import com.compses.model.SProvince;
import com.compses.model.TBaseProvince;
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
public class SProvinceService {

    @Autowired
    private BaseCommonService baseCommonService;

    public List<TBaseProvince> selectAll(){
        TBaseProvince sProvince = new TBaseProvince();
        return baseCommonService.loadData(sProvince);
    }



}
