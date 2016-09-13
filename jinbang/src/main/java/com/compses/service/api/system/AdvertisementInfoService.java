package com.compses.service.api.system;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.AdvertisementInfo;
import com.compses.service.common.BaseCommonService;
import com.compses.util.StringUtils;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nini on 2016/5/1.
 */
@Service
@Transactional
public class AdvertisementInfoService extends BaseCommonService {

    @Autowired
    public IBaseCommonDAO baseCommonDAO;

    public void addOrUpdate(AdvertisementInfo advertisementInfo){
        if (StringUtils.isEmpty(advertisementInfo.getAdvertisementId())){
            baseCommonDAO.save(advertisementInfo);
        }else {
            if (advertisementInfo.getAdverPhotos().isEmpty()){
                AdvertisementInfo old = baseCommonDAO.selectOne(advertisementInfo);
                advertisementInfo.setAdverPhotos(old.getAdverPhotos());
            }
            baseCommonDAO.update(advertisementInfo);
        }
    }
}
