package com.club.web.datamodel.db.dao.mapper;


import com.club.web.datamodel.db.arg.WfDbTableArg;
import com.club.web.datamodel.db.po.WfDbTablePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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

    int deleteByPrimaryKey(String key);

    WfDbTablePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfDbTablePO record);

    int updateByPrimaryKey(WfDbTablePO record);
}