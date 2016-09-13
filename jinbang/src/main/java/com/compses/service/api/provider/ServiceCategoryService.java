package com.compses.service.api.provider;

import com.compses.dao.provider.IServiceCategoryDao;
import com.compses.model.ServiceCategory;
import com.compses.service.common.BaseCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/7/24 0024.
 */

@Service
@Transactional
public class ServiceCategoryService extends BaseCommonService{

    @Autowired
    private IServiceCategoryDao serviceCategoryDao;

    public List<ServiceCategory> listCategoryByConditions(ServiceCategory serviceCategory){
        return this.loadData(serviceCategory);
    }

    public ServiceCategory getById(String categoryId){
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setServiceCategoryId(categoryId);
        serviceCategory = serviceCategoryDao.selectOne(serviceCategory);
        return serviceCategory;
    }
}
