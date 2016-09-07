package com.club.web.works.controller;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.client.service.IAccountService;
import com.club.web.client.service.IClientService;
import com.club.web.client.service.IIntegralMangerService;
import com.club.web.common.service.IBaseService;
import com.club.web.datamodel.service.IWfDbTableService;
import com.club.web.finance.service.AuditService;
import com.club.web.works.domain.PersonalWorksDo;
import com.club.web.works.service.PersonalWorksService;
import com.club.web.works.vo.PersonalWorksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * <Description>自动生成代码 <br>
 * 
 * @author lifei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年12月11日 <br>
 * @since V1.0<br>
 */

@Controller
@RequestMapping("/personalWorks")
public class PersonalWorksController {

    private final ClubLogManager logger = ClubLogManager
            .getLogger(this.getClass());

	@Autowired
	private PersonalWorksService personalWorksService;

	@RequestMapping("/mobile/selectPersonalWorksPageList")
	@ResponseBody
	public Page<PersonalWorksVo> selectPersonalWorksPageList(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		personalWorksDo.setSelectColumns("works_id,works_title,works_cover,works_type");
		return personalWorksService.selectPageList(personalWorksDo);
	}
	@RequestMapping("/mobile/selectPersonalWorksList")
	@ResponseBody
	public List<PersonalWorksVo> selectPersonalWorksList(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		personalWorksDo.setSelectColumns("works_id,works_title,works_cover,works_type");
		return personalWorksService.selectList(personalWorksDo);
	}


}
