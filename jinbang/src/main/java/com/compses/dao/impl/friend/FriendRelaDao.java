package com.compses.dao.impl.friend;

import com.compses.dao.friend.IFriendRelaDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dto.FriendRelaDTO;
import com.compses.model.FriendRela;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27 0027.
 */

@Repository
public class FriendRelaDao extends BaseCommonDAO implements IFriendRelaDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<FriendRelaDTO> listFriend(String userId,String status){
        String sql= DBUtils.getSql("FriendRela", "selectList");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("status", status);
        return  DBUtils.query(sql,  paramMap, namedParameterJdbcTemplate, FriendRelaDTO.class);
    }

    public List<FriendRelaDTO> selectListByConditions(String condition){
        String sql= DBUtils.getSql("FriendRela", "selectListByConditions");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("condition", condition);
        return  DBUtils.query(sql,  paramMap, namedParameterJdbcTemplate, FriendRelaDTO.class);
    }

    public void deleteByUserConditions(FriendRela rela){
        String sql= DBUtils.getSql("FriendRela", "deleteByUserConditions");
        DBUtils.update(sql, namedParameterJdbcTemplate,rela);
    }

    public List<FriendRelaDTO> findUserByConditions(String condition){
        String sql= DBUtils.getSql("FriendRela", "findUserByConditions");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("condition", "%"+condition+"%");
        return  DBUtils.query(sql,  paramMap, namedParameterJdbcTemplate, FriendRelaDTO.class);
    }

}
