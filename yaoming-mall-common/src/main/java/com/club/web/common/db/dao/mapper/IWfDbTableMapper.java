package com.club.web.common.db.dao.mapper;

import com.club.web.common.db.arg.WfDbTableArg;
import com.club.web.common.db.po.WfDbTablePO;

import java.util.*;
import java.math.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface IWfDbTableMapper {
    List<Integer> countByArg(WfDbTableArg arg);

    int deleteByArg(WfDbTableArg arg);

    List<WfDbTablePO> selectByArg(WfDbTableArg arg);

    int updateByArgSelective(@Param("record") WfDbTablePO record,
            @Param("arg") WfDbTableArg arg);

    int updateByArg(@Param("record") WfDbTablePO record,
            @Param("arg") WfDbTableArg arg);

    List<WfDbTablePO> selectByArgAndPage(WfDbTableArg arg, RowBounds rowBound);

    int insert(WfDbTablePO record);

    int insertSelective(WfDbTablePO record);

    int insertBatch(@Param("list") List<WfDbTablePO> records);

    int deleteByPrimaryKey(Integer id);

    WfDbTablePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfDbTablePO record);

    int updateByPrimaryKey(WfDbTablePO record);
}