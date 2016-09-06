package com.club.web.store.service;

import java.util.Map;

import com.club.web.store.vo.SettleTimeVo;

public interface SettleTimeService {
    Map<String,Object> saveOrUpdateSettleTime(SettleTimeVo settleTimeVo);
    SettleTimeVo getSettleTime();
}
