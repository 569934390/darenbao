package com.club.web.module.service;

import java.util.Map;

import com.club.core.common.Page;
import com.club.web.module.vo.VersionVo;
/**
 * 版本管理服务接口
 * @author zhuzd
 *
 */
public interface VersionService {
	
	public VersionVo getLastVersionVo(Integer platform);

	public Page<Map<String, Object>> list(Page<Map<String, Object>> page);

	public String updateEffect(String ids, String effect, String username);

	public Map<String, Object> deleteByIds(String ids);

	public boolean add(String username, VersionVo versionVo);

	public boolean modify(String username, VersionVo versionVo);
		
}
