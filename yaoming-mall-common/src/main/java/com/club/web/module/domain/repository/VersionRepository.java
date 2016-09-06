package com.club.web.module.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.module.domain.VersionDo;
import com.club.web.module.domain.VersionDo2;
/**
 * 版本管理仓库接口
 * @author zhuzd
 *
 */
public interface VersionRepository {
	
	public VersionDo2 getLastVersionDo(Integer platform);
	
	public int queryTotalByMap(Map<String, Object> con);

	public List<VersionDo> queryDoByMap(Map<String, Object> con);

	public void updateEffect(VersionDo versionDo);

	public List<VersionDo> findDoListByIds(String ids);

	public List<VersionDo> findDoAllAble(Integer platform);

	public void deleteById(Long id);

	public void add(VersionDo versionDo);
	
	public void update(VersionDo versionDo);

	public VersionDo2 findDoById(Long id);

	public VersionDo2 findDoByCode(String versionCode);


	 
}
