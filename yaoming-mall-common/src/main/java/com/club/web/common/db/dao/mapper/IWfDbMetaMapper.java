package com.club.web.common.db.dao.mapper;

import com.club.web.common.db.arg.WfDbMetaArg;
import com.club.web.common.db.po.WfDbMetaPO;

import java.util.*;
import java.math.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IWfDbMetaMapper {
    List<Integer> countByArg(WfDbMetaArg arg);

    int deleteByArg(WfDbMetaArg arg);

    List<WfDbMetaPO> selectByArg(WfDbMetaArg arg);

    int updateByArgSelective(@Param("record") WfDbMetaPO record,
            @Param("arg") WfDbMetaArg arg);

    int updateByArg(@Param("record") WfDbMetaPO record,
            @Param("arg") WfDbMetaArg arg);

    List<WfDbMetaPO> selectByArgAndPage(WfDbMetaArg arg, RowBounds rowBound);

    int insert(WfDbMetaPO record);

    int insertSelective(WfDbMetaPO record);

    int insertBatch(@Param("list") List<WfDbMetaPO> records);

    int deleteByPrimaryKey(Integer id);

    WfDbMetaPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfDbMetaPO record);

    int updateByPrimaryKey(WfDbMetaPO record);
}