package com.club.web.autoRepeat.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.autoRepeat.dao.base.AutorepeatMapper;
import com.club.web.autoRepeat.vo.AutorepeatVo;

public interface AutoRepeatExtendMapper extends AutorepeatMapper{

	public List<AutorepeatVo> queryAutoRepeatPage(Map<String, Object> map);
	public Long queryAutoRepeatCountPage(Map<String, Object> map);
	public AutorepeatVo selectRepeatBykeyword(Map<String, Object> map);
	public List<AutorepeatVo> querykeyword(Map<String, Object> map);
	public List<AutorepeatVo> selectAllKeyword();
}
