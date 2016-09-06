package com.club.web.datasource.db.dao.mapper;


import com.club.web.datasource.db.arg.WfDataSetArg;
import com.club.web.datasource.db.po.WfDataSetPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IWfDataSetMapper {
    List<Integer> countByArg(WfDataSetArg arg);

    int deleteByArg(WfDataSetArg arg);

    List<WfDataSetPO> selectByArg(WfDataSetArg arg);

    int updateByArgSelective(@Param("record") WfDataSetPO record,
                             @Param("arg") WfDataSetArg arg);

    int updateByArg(@Param("record") WfDataSetPO record,
                    @Param("arg") WfDataSetArg arg);

    List<WfDataSetPO> selectByArgAndPage(WfDataSetArg arg, RowBounds rowBound);

    int insert(WfDataSetPO record);

    int insertSelective(WfDataSetPO record);

    int insertBatch(@Param("list") List<WfDataSetPO> records);

    int deleteByPrimaryKey(Integer id);

    WfDataSetPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfDataSetPO record);

    int updateByPrimaryKey(WfDataSetPO record);
}