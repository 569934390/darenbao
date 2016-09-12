package com.compses.dao.provider;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.ServiceInfo;
import com.compses.model.ServiceInfoDTO;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public interface IServiceInfoDao extends IBaseCommonDAO{

    Page<ServiceInfo> listServiceByPublisherId(String publisherId,Page<ServiceInfo> page);

    ServiceInfo getServiceInfoById(String serviceId,String userId);

    List<ServiceInfo> listServiceByPublisherId(String publisherId);

    Page<ServiceInfo> listNearbyService(String[] userIds, Page<ServiceInfo> page);


    public Page<ServiceInfoDTO> findLastServiceInfos(String userId,String cityName, String categoryNames, Page<ServiceInfoDTO> page);
}
