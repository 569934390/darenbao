package com.club.web.common.db.dao.mapper;

import com.club.web.common.db.arg.WfDbColumnsArg;
import com.club.web.common.db.po.WfDbColumnsPO;

import java.util.*;
import java.math.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IWfDbColumnsMapper {
    List<Integer> countByArg(WfDbColumnsArg arg);

    int deleteByArg(WfDbColumnsArg arg);

    List<WfDbColumnsPO> selectByArg(WfDbColumnsArg arg);

    int updateByArgSelective(@Param("record") WfDbColumnsPO record,
            @Param("arg") WfDbColumnsArg arg);

    int updateByArg(@Param("record") WfDbColumnsPO record,
            @Param("arg") WfDbColumnsArg arg);

    List<WfDbColumnsPO> selectByArgAndPage(WfDbColumnsArg arg, RowBounds rowBound);

    int insert(WfDbColumnsPO record);

    int insertSelective(WfDbColumnsPO record);

    int insertBatch(@Param("list") List<WfDbColumnsPO> records);

    int deleteByPrimaryKey(Integer id);

    WfDbColumnsPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfDbColumnsPO record);

    int updateByPrimaryKey(WfDbColumnsPO record);
}