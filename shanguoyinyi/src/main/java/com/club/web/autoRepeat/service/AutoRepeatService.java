package com.club.web.autoRepeat.service;

import java.util.Map;

import com.club.core.common.Page;
import com.club.web.autoRepeat.vo.AutorepeatVo;
import com.club.web.autoRepeat.vo.DefaultrepeatVo;

public interface AutoRepeatService {
	Page<Map<String, Object>> selectAutoRepeat(Page<Map<String, Object>> page);
	void addAutoRepeat(AutorepeatVo autorepeatVo);
	void editAutoRepeat(AutorepeatVo autorepeatVo);
	void deleteAutoRepeat(String idStr);
	String selectRepeat(String phases,Long id);
	void editDefaultRepeat(DefaultrepeatVo defaultrepeatVo);
	Page<Map<String, Object>> selectDefaultRepeat(Page<Map<String, Object>> page);
}
