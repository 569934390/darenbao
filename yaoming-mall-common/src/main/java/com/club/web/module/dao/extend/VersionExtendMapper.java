package com.club.web.module.dao.extend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.web.module.dao.base.VersionMapper;
import com.club.web.module.dao.base.po.Version;
/**
 * 版本管理Dao扩展类
 * @author zhuzd
 *
 */
public interface VersionExtendMapper extends VersionMapper{

	Version findLastVersion(@Param("platform")Integer platform);

	int queryTotalByMap(Map<String, Object> con);

	List<Version> queryPoByMap(Map<String, Object> con);

	List<Version> findListByIds(List<Long> ids);

	List<Version> findAllAble(@Param("platform")Integer platform);

	Version findByCode(String code);
    
}