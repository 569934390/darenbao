package com.club.web.datasource.db.dao.mapper;


import com.club.web.datasource.db.arg.WfGeneralTableArg;
import com.club.web.datasource.db.po.WfGeneralTablePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IWfGeneralTableMapper {
    List<Integer> countByArg(WfGeneralTableArg arg);

    int deleteByArg(WfGeneralTableArg arg);

    List<WfGeneralTablePO> selectByArg(WfGeneralTableArg arg);

    int updateByArgSelective(@Param("record") WfGeneralTablePO record,
                             @Param("arg") WfGeneralTableArg arg);

    int updateByArg(@Param("record") WfGeneralTablePO record,
                    @Param("arg") WfGeneralTableArg arg);

    List<WfGeneralTablePO> selectByArgAndPage(WfGeneralTableArg arg, RowBounds rowBound);

    int insert(WfGeneralTablePO record);

    int insertSelective(WfGeneralTablePO record);

    int insertBatch(@Param("list") List<WfGeneralTablePO> records);

    int deleteByPrimaryKey(Integer id);

    WfGeneralTablePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfGeneralTablePO record);

    int updateByPrimaryKey(WfGeneralTablePO record);
}