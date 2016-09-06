package com.club.web.store.dao.extend;

import java.util.List;

import com.club.web.store.dao.base.SettleTimeMapper;
import com.club.web.store.dao.base.po.SettleTime;

public interface SettleTimeExtendMapper extends SettleTimeMapper{
   List<SettleTime> selectByPage();
}