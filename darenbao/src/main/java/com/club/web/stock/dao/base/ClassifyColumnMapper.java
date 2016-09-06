package com.club.web.stock.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.po.ClassifyColumn;
import com.club.web.stock.vo.ClassifyColumnVo;

public interface ClassifyColumnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassifyColumn record);

    int insertSelective(ClassifyColumn record);

    ClassifyColumn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClassifyColumn record);

    int updateByPrimaryKey(ClassifyColumn record);

	int queryTotalByMap(Map<String, Object> con);

	List<ClassifyColumnVo> queryPoByMap(Map<String, Object> con);

	void deleteByList(@Param("ids") List<Long> ids);
}