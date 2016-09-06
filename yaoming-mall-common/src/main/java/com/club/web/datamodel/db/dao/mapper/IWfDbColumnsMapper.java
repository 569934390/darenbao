package com.club.web.datamodel.db.dao.mapper;


import com.club.web.datamodel.db.arg.WfDbColumnsArg;
import com.club.web.datamodel.db.po.WfDbColumnsPO;
import com.club.framework.exception.BaseAppException;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IWfDbColumnsMapper {
    List<Integer> countByArg(WfDbColumnsArg arg);

    int deleteByArg(WfDbColumnsArg arg);

    List<WfDbColumnsPO> selectByArg(WfDbColumnsArg arg);

    int updateByArgSelective(@Param("record") WfDbColumnsPO record,
                             @Param("arg") WfDbColumnsArg arg);

    int updateByArg(@Param("record") WfDbColumnsPO record,
                    @Param("arg") WfDbColumnsArg arg);

    List<WfDbColumnsPO> selectByArgAndPage(WfDbColumnsArg arg, RowBounds rowBound);

    List<WfDbColumnsPO> queryRecordByPageWithDbName(WfDbColumnsArg arg, RowBounds rowBound);

    int insert(WfDbColumnsPO record);

    int saveOrUpdate(WfDbColumnsPO record);

    int insertSelective(WfDbColumnsPO record);

    int insertBatch(@Param("list") List<WfDbColumnsPO> records);

    int deleteByPrimaryKey(String id);

    WfDbColumnsPO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WfDbColumnsPO record);

    int updateByPrimaryKey(WfDbColumnsPO record);
}