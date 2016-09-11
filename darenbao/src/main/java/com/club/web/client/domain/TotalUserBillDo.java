package com.club.web.client.domain;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.web.client.vo.TotalUserBillVo;
import com.club.web.common.domain.IBaseDo;

import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class TotalUserBillDo implements IBaseDo<TotalUserBillVo> {
    @Override
    public Page<TotalUserBillVo> selectPageList() throws BaseAppException {
        return null;
    }

    @Override
    public List<TotalUserBillVo> selectList() throws BaseAppException {
        return null;
    }

    @Override
    public TotalUserBillVo selectOne() throws BaseAppException {
        return null;
    }

    @Override
    public int insert() throws BaseAppException {
        return 0;
    }

    @Override
    public int update() throws BaseAppException {
        return 0;
    }

    @Override
    public int delete() throws BaseAppException {
        return 0;
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
