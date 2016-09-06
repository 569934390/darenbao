package com.club.web.module.domain.repository;

import com.club.web.module.domain.CommonTextDo;
/**
 * 通用文本仓库接口
 * @author zhuzd
 *
 */
public interface CommonTextRepository {

	CommonTextDo findTextDoByType(int type);

	CommonTextDo findTextDoById(Long id);

	int add(CommonTextDo commonTextDo);

	int modify(CommonTextDo commonTextDo);

}
