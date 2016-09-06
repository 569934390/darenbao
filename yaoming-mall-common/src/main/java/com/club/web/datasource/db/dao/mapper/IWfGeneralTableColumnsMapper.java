package com.club.web.datasource.db.dao.mapper;


import com.club.web.datasource.db.arg.WfGeneralTableColumnsArg;
import com.club.web.datasource.db.po.WfGeneralTableColumnsPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IWfGeneralTableColumnsMapper {
    List<Integer> countByArg(WfGeneralTableColumnsArg arg);

    int deleteByArg(WfGeneralTableColumnsArg arg);

    List<WfGeneralTableColumnsPO> selectByArg(WfGeneralTableColumnsArg arg);

    int updateByArgSelective(@Param("record") WfGeneralTableColumnsPO record,
                             @Param("arg") WfGeneralTableColumnsArg arg);

    int updateByArg(@Param("record") WfGeneralTableColumnsPO record,
                    @Param("arg") WfGeneralTableColumnsArg arg);

    List<WfGeneralTableColumnsPO> selectByArgAndPage(WfGeneralTableColumnsArg arg, RowBounds rowBound);

    int insert(WfGeneralTableColumnsPO record);

    int insertSelective(WfGeneralTableColumnsPO record);

    int insertBatch(@Param("list") List<WfGeneralTableColumnsPO> records);

    int deleteByPrimaryKey(Integer id);

    WfGeneralTableColumnsPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfGeneralTableColumnsPO record);

    int updateByPrimaryKey(WfGeneralTableColumnsPO record);
}