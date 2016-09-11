package com.club.web.client.dao.repository;

import com.club.web.client.domain.repository.IMobileUserInfoRepository;
import com.club.web.common.domain.repository.impl.BaseRepositoryImpl;

/**
 * Created by Administrator on 2016/9/11.
 */
public class TotalUserBillRepository extends BaseRepositoryImpl implements IMobileUserInfoRepository {
    public TotalUserBillRepository() {
        super("total_user_bill");
    }
}
