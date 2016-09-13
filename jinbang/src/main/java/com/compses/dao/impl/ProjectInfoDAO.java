package com.compses.dao.impl;

import com.compses.dto.Page;
import com.compses.model.ProjectInfo;
import com.compses.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;

@Resource
public class ProjectInfoDAO {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Page<ProjectInfo> reviews(ProjectInfo projectInfo,Page<ProjectInfo> page) throws Exception {
		String sql= DBUtils.getSql("projectInfo.selectList",namedParameterJdbcTemplate);
		return   DBUtils.queryPage(sql, page, projectInfo, namedParameterJdbcTemplate, ProjectInfo.class);
	}

}
