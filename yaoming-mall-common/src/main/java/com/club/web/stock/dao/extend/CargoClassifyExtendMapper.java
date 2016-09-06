package com.club.web.stock.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.stock.dao.base.CargoClassifyMapper;
import com.club.web.stock.dao.base.po.CargoClassify;

/**
 * 货品分类Dao扩展类
 * 
 * @author zhuzd
 *
 */
public interface CargoClassifyExtendMapper extends CargoClassifyMapper {

	List<CargoClassify> findListByIds(List<Long> ids);

	CargoClassify findByIdAndStatus(@Param("id") Long id, @Param("status") Integer status);

	List<CargoClassify> findNoIds(List<Long> ids);

	List<CargoClassify> findByParentIdAndStatus(@Param("parentId") Long parentId, @Param("status") Integer status);

	List<CargoClassify> findAllPoByStatus(@Param("status") Integer status);

	String queryNameById(@Param("id") long id);
}