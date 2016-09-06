package com.club.web.module.service;

import com.club.web.module.vo.CommonTextVo;

/**
 * 通用文本服务接口
 * @author zhuzd
 *
 */
public interface CommonTextService {

	public CommonTextVo findTextVoByType(int dbData);

	public boolean addOrModify(CommonTextVo commonTextVo);
	
}
