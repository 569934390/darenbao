package com.club.web.autoRepeat.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.autoRepeat.domain.AutorepeatDo;
import com.club.web.autoRepeat.domain.DefaultrepeatDo;
import com.club.web.autoRepeat.vo.AutorepeatVo;
import com.club.web.autoRepeat.vo.DefaultrepeatVo;

public interface AutoRepeatRepository {
	public List<AutorepeatVo> queryAutoRepeatPage(Map<String, Object> map);
	public Long queryAutoRepeatCountPage(Map<String, Object> map);
	void addAutoRepeat(AutorepeatDo autorepeatDo);
	void updateAutoRepeat(AutorepeatDo autorepeatDo);
	void deleteById(Long id);
	public AutorepeatVo selectRepeatBykeyword(Map<String, Object> map);
	void updateDefaultRepeat(DefaultrepeatDo defaultrepeatDo);
	List<DefaultrepeatVo> queryDefaultRepeatPage(Map<String, Object> map);
	Long queryDefaultRepeatCountPage(Map<String, Object> map);
}
