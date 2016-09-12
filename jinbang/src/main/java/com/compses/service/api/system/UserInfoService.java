package com.compses.service.api.system;

import com.compses.dao.IUserDao;
import com.compses.model.DopPrivilegeUser;
import com.compses.service.common.BaseCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 */

@Service
@Transactional
public class UserInfoService extends BaseCommonService {

    @Autowired
    private IUserDao userDao;


    public DopPrivilegeUser getByEnterpriseCode(String enterpriseCode){
        DopPrivilegeUser user = new DopPrivilegeUser();
        List<DopPrivilegeUser> userList = this.loadData(user);
        if (userList.size()==0){
            user = null;
        }else {
            user = userList.get(0);
        }
        return user;
    }

    public DopPrivilegeUser update(DopPrivilegeUser user)throws Exception{
        userDao.update(user);
        user = userDao.selectOne(user);
        return user;
    }

    public DopPrivilegeUser save(DopPrivilegeUser user)throws Exception{
        userDao.save(user);
        user = userDao.selectOne(user);
        return user;
    }

}
