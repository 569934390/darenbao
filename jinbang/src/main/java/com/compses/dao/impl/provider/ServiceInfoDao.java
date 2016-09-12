package com.compses.dao.impl.provider;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.provider.IServiceInfoDao;
import com.compses.dto.Page;
import com.compses.model.ServiceInfo;
import com.compses.model.ServiceInfoDTO;
import com.compses.util.DBUtils;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/25 0025.
 */

@Repository
public class ServiceInfoDao extends BaseCommonDAO implements IServiceInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Page<ServiceInfo> listServiceByPublisherId(String publisherId,Page<ServiceInfo> page){
        String sql= DBUtils.getSql("ServiceInfo", "listServiceListForMobile");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("publisherId", publisherId);
        return   DBUtils.queryPage(sql, page, paramMap, namedParameterJdbcTemplate, ServiceInfo.class);
    }

    public ServiceInfo getServiceInfoById(String serviceId,String userId){
        String sql= DBUtils.getSql("ServiceInfo", "getServiceInfoById");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("serviceId", serviceId);
        paramMap.put("userId", userId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, ServiceInfo.class);
    }

    public List<ServiceInfo> listServiceByPublisherId(String publisherId){
        String sql= DBUtils.getSql("ServiceInfo", "listServiceByUser");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("publisherId", publisherId);
        return   DBUtils.query(sql, paramMap, namedParameterJdbcTemplate, ServiceInfo.class);
    }

    public Page<ServiceInfo> listNearbyService(String[] userIds, Page<ServiceInfo> page){
        String sql= DBUtils.getSql("ServiceInfo", "listServiceListForMobile");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        List<String> userIdList = new ArrayList<String>();
        for (String userId :userIds){
            userIdList.add(userId);
        }
        paramMap.put("userIds", userIdList);
        return   DBUtils.queryPage(sql, page, paramMap, namedParameterJdbcTemplate, ServiceInfo.class);
    }

    public Page<ServiceInfoDTO> findLastServiceInfos(String userId,String cityName, String categoryNames, Page<ServiceInfoDTO> page) {

        String sql = DBUtils.getSql("ServiceInfo", "selectLastServiceForApp");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String categoryNameArray[] = null;
        List<String> categoryNameList = new ArrayList<String>();
        if (!StringUtils.isEmpty(categoryNames)) {
            categoryNameArray = categoryNames.split(",");
            for(int i = 0 ; i<categoryNameArray.length;i++){
                categoryNameList.add(categoryNameArray[i]);
            }
        }
        paramMap.put("serviceCityName", cityName);
        paramMap.put("categoryNames", categoryNameList);
        paramMap.put("userId", userId);
        return DBUtils.queryPage(sql, page, paramMap, namedParameterJdbcTemplate, ServiceInfoDTO.class);

    }
}
