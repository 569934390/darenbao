package com.compses.service.api;

import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.impl.ProjectInfoDAO;
import com.compses.dto.Page;
import com.compses.model.ProjectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectInfoService {
	
	@Autowired
	private IBaseCommonDAO baseCommonDAO;
	@Autowired
	private ProjectInfoDAO projectInfoDAO;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public Page<ProjectInfo> reviews(ProjectInfo projectInfo,Page<ProjectInfo> page) throws Exception {
		logger.info("项目评论列表");
		return projectInfoDAO.reviews(projectInfo,page);
	}
}
