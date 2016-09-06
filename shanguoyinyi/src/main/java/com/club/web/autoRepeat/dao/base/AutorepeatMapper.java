package com.club.web.autoRepeat.dao.base;

import com.club.web.autoRepeat.dao.base.po.Autorepeat;

public interface AutorepeatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Autorepeat record);

    int insertSelective(Autorepeat record);

    Autorepeat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Autorepeat record);

    int updateByPrimaryKey(Autorepeat record);
}