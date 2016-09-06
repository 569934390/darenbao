package com.club.web.autoRepeat.dao.base;

import com.club.web.autoRepeat.dao.base.po.Defaultrepeat;

public interface DefaultrepeatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Defaultrepeat record);

    int insertSelective(Defaultrepeat record);

    Defaultrepeat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Defaultrepeat record);

    int updateByPrimaryKey(Defaultrepeat record);
}