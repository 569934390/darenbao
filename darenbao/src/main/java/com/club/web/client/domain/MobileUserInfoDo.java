package com.club.web.client.domain;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.client.dao.repository.MobileUserInfoRepository;
import com.club.web.client.vo.MobileUserInfoVo;
import com.club.web.common.domain.IBaseDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
@Configurable
public class MobileUserInfoDo extends MobileUserInfoVo implements IBaseDo<MobileUserInfoVo> {
    @Autowired
    MobileUserInfoRepository mobileUserInfoRepository;
    @Override
    public Page<MobileUserInfoVo> selectPageList() throws BaseAppException {
        return mobileUserInfoRepository.selectPageList(this,MobileUserInfoVo.class);
    }

    @Override
    public List<MobileUserInfoVo> selectList() throws BaseAppException {
        return mobileUserInfoRepository.selectList(this,MobileUserInfoVo.class);
    }

    @Override
    public MobileUserInfoVo selectOne() throws BaseAppException {
        return mobileUserInfoRepository.selectOne(this,MobileUserInfoVo.class);
    }

    @Override
    public int insert() throws BaseAppException {
        return this.insert();
    }

    @Override
    public int update() throws BaseAppException {
        return this.update();
    }

    @Override
    public int delete() throws BaseAppException {
        return this.delete();
    }

    @Override
    public int delete(long id) throws BaseAppException {
        return 0;
    }

    @Override
    public int delete(String id) throws BaseAppException {
        return 0;
    }
}
