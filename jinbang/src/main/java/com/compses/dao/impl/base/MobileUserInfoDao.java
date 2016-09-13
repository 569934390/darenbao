package com.compses.dao.impl.base;

import com.compses.dao.base.IMobileUserInfoDao;
import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dto.Page;
import com.compses.model.MobileUserInfo;
import com.compses.util.DBUtils;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
@Repository
public class MobileUserInfoDao extends BaseCommonDAO implements IMobileUserInfoDao {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MobileUserInfo getByUserId(String userId){
        String sql= DBUtils.getSql("MobileUserInfo", "getByUserId");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, MobileUserInfo.class);
    }

    public MobileUserInfo getByMobile(MobileUserInfo mobileUserInfo){
        String sql= DBUtils.getSql("MobileUserInfo", "selectByMobile");
        return  DBUtils.queryForObject(sql,  mobileUserInfo, namedParameterJdbcTemplate, MobileUserInfo.class);
    }

    public MobileUserInfo getByInvitationCode(String invitationCode){
        String sql= DBUtils.getSql("MobileUserInfo", "getByInvitationCode");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("invitationCode", invitationCode);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, MobileUserInfo.class);
    }

    public MobileUserInfo getByParentUserId(String parentUserId){
        String sql= DBUtils.getSql("MobileUserInfo", "getByParentUserId");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("parentUserId", parentUserId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, MobileUserInfo.class);
    }

    public List<MobileUserInfo> getSubordinate(String userId){
        String sql= DBUtils.getSql("MobileUserInfo", "selectList");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("parentUserId", userId);
        return  DBUtils.query(sql, paramMap, namedParameterJdbcTemplate, MobileUserInfo.class);
    }

    public MobileUserInfo getParent(String userId){
        String sql= DBUtils.getSql("MobileUserInfo", "getParent");
        Map<String,Object> paramMap=new HashMap<String, Object>();
        paramMap.put("userId", userId);
        return  DBUtils.queryForObject(sql,  paramMap, namedParameterJdbcTemplate, MobileUserInfo.class);
    }

    public Page<MobileUserInfo> getFirstSub(MobileUserInfo mobileUserInfo ,Page<MobileUserInfo> page){
        String sql = DBUtils.getSql("MobileUserInfo","getFirstSub");
        return DBUtils.queryPage(sql,page,mobileUserInfo,namedParameterJdbcTemplate,MobileUserInfo.class);
    }

    public Page<MobileUserInfo> getSecondSub(MobileUserInfo mobileUserInfo ,Page<MobileUserInfo> page){
        String sql = DBUtils.getSql("MobileUserInfo","getSecondSub");
        return DBUtils.queryPage(sql,page,mobileUserInfo,namedParameterJdbcTemplate,MobileUserInfo.class);
    }

    public Page<MobileUserInfo> getThridSub(MobileUserInfo mobileUserInfo ,Page<MobileUserInfo> page){
        String sql = DBUtils.getSql("MobileUserInfo","getThridSub");
        return DBUtils.queryPage(sql,page,mobileUserInfo,namedParameterJdbcTemplate,MobileUserInfo.class);
    }
}
