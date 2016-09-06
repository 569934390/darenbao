package com.club.web.store.domain.repository;

import com.club.web.store.domain.SettleTimeDo;

public interface SettleTimeRepository {
int insert( SettleTimeDo  settleTimeDo);
int update(SettleTimeDo  settleTimeDo);
}
