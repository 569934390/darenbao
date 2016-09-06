package com.club.web.common.db.dao.mapper;

import com.club.web.common.db.arg.CodeValueArg;
import com.club.web.common.db.po.CodeValuePO;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ICodeValueMapper {
    
    List<Integer> countByArg(CodeValueArg arg);

    List<CodeValuePO> selectByArg(CodeValueArg arg);

    CodeValuePO selectByPrimaryKey(String id);

    List<CodeValuePO> selectByArgAndPage(CodeValueArg arg, RowBounds rowBound);

    int insert(CodeValuePO record);

    int insertSelective(CodeValuePO record);

    int insertBatch(@Param("list") List<CodeValuePO> records);

    int updateByArgSelective(@Param("record") CodeValuePO record,
            @Param("arg") CodeValueArg arg);

    int updateByArg(@Param("record") CodeValuePO record,
            @Param("arg") CodeValueArg arg);

    int updateByPrimaryKeySelective(CodeValuePO record);

    int updateByPrimaryKey(CodeValuePO record);
    
    int deleteByArg(CodeValueArg arg);
    
    int deleteByPrimaryKey(String id);
    
}