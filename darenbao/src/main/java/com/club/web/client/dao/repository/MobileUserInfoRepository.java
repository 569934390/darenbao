package com.club.web.client.dao.repository;

import com.club.web.client.domain.repository.IMobileUserInfoRepository;
import com.club.web.common.domain.repository.impl.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/9/11.
 */
@Repository
public class MobileUserInfoRepository extends BaseRepositoryImpl implements IMobileUserInfoRepository {
    public MobileUserInfoRepository() {
        super("mobile_user_info");
    }
}
