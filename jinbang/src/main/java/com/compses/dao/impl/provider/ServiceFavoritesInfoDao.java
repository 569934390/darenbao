package com.compses.dao.impl.provider;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.provider.IServiceFavoritesInfoDao;
import com.compses.dto.Page;
import com.compses.dto.ServiceFavoritesInfoDto;
import com.compses.model.ServiceFavoritesInfo;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
@Repository
public class ServiceFavoritesInfoDao extends BaseCommonDAO implements IServiceFavoritesInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void deleteFavoriteService(ServiceFavoritesInfo serviceFavoritesInfo){
        String sql= DBUtils.getSql("ServiceFavoritesInfo", "deleteFavoriteService");
        DBUtils.update(sql, namedParameterJdbcTemplate, serviceFavoritesInfo);
    }

    public Page<ServiceFavoritesInfoDto> listForPage(String userId,Page<ServiceFavoritesInfoDto> page){
        String sql= DBUtils.getSql("ServiceFavoritesInfo", "selectList");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryPage(sql, page, paramMap, namedParameterJdbcTemplate, ServiceFavoritesInfoDto.class);
    }
}
