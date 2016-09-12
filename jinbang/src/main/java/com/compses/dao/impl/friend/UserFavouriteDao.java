package com.compses.dao.impl.friend;

import com.compses.dao.friend.IUserFavouriteDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.UserFavourite;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
@Repository
public class UserFavouriteDao extends BaseCommonDAO implements IUserFavouriteDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void deleteByUserId(UserFavourite userFavourite){
        String sql= DBUtils.getSql("UserFavourite", "deleteByUserId");
        DBUtils.query(sql,  userFavourite, namedParameterJdbcTemplate, UserFavourite.class);
    }

    public Page<UserFavourite> listPageForFavourite(String userId,Page<UserFavourite> page){
        String sql= DBUtils.getSql("UserFavourite", "selectList");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryPage(sql, page, paramMap, namedParameterJdbcTemplate, UserFavourite.class);
    }

    public UserFavourite listByConditions(String userId,String favouriteUserId){
        String sql= DBUtils.getSql("UserFavourite", "listByConditions");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("favouriteUserId", favouriteUserId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, UserFavourite.class);
    }
}
