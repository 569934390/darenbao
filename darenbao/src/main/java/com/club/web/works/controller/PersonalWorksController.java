package com.club.web.works.controller;

import com.club.core.common.Page;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.web.works.constants.WorksCategoryEnum;
import com.club.web.works.domain.PersonalWorksDo;
import com.club.web.works.service.PersonalWorksService;
import com.club.web.works.vo.PersonalWorksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
		return personalWorksService.selectPageList(personalWorksDo);
	}

	@RequestMapping("/mobile/selectPersonalWorksList")
	@ResponseBody
	public List<PersonalWorksVo> selectPersonalWorksList(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return personalWorksService.selectList(personalWorksDo);
	}
	@RequestMapping("/mobile/select")
	@ResponseBody
	public PersonalWorksVo select(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return personalWorksService.selectOne(personalWorksDo);
	}

	@RequestMapping("/mobile/create")
	@ResponseBody
	public int create(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return personalWorksService.insert(personalWorksDo);
	}
	@RequestMapping("/mobile/update")
	@ResponseBody
	public int update(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return personalWorksService.update(personalWorksDo);
	}
	@RequestMapping("/mobile/delete")
	@ResponseBody
	public int delete(PersonalWorksDo personalWorksDo) throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return personalWorksService.delete(personalWorksDo);
	}
	@RequestMapping("/mobile/getWorksCategory")
	@ResponseBody
	public String getWorksCategory() throws BaseAppException, NoSuchMethodException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return WorksCategoryEnum.toJsonString();

	}


}
