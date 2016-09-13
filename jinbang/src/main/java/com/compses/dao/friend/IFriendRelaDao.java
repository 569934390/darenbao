package com.compses.dao.friend;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dto.FriendRelaDTO;
import com.compses.model.FriendRela;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public interface IFriendRelaDao extends IBaseCommonDAO {


    List<FriendRelaDTO> listFriend(String userId,String status);

    List<FriendRelaDTO> selectListByConditions(String condition);

    void deleteByUserConditions(FriendRela rela);

    List<FriendRelaDTO> findUserByConditions(String condition);

}
