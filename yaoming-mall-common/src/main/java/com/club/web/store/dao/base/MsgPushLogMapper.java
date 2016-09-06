package com.club.web.store.dao.base;

import com.club.web.store.dao.base.po.MsgPushLog;
import com.club.web.store.dao.base.po.MsgPushLogWithBLOBs;

public interface MsgPushLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MsgPushLogWithBLOBs record);

    int insertSelective(MsgPushLogWithBLOBs record);

    MsgPushLogWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MsgPushLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MsgPushLogWithBLOBs record);

    int updateByPrimaryKey(MsgPushLog record);
}