package com.compses.action.api;

import com.compses.dto.Page;
import com.compses.framework.ResultData;
import com.compses.model.ProjectInfo;
import com.compses.service.api.ProjectInfoService;
import com.compses.service.common.BaseCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("projectinfo")
public class ProjectInfoController {
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseCommonService baseCommonService;

	@Autowired
	private ProjectInfoService projectInfoService;


	//项目评论列表
	@RequestMapping(value="reviews.do",method = RequestMethod.GET)
	@ResponseBody
	public Page<ProjectInfo> findAlbums(ProjectInfo projectInfo,Page<ProjectInfo> page) throws Exception {
		Page<ProjectInfo> albumInfoPage = projectInfoService.reviews(projectInfo, page);
		return albumInfoPage;
	}
}
