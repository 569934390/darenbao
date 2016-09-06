package com.club.web.autoRepeat.dao.extend;

import java.util.List;
import java.util.Map;

import com.club.web.autoRepeat.dao.base.DefaultrepeatMapper;
import com.club.web.autoRepeat.vo.DefaultrepeatVo;

public interface DefaultRepeatExtendMapper extends DefaultrepeatMapper {
   
	public List<DefaultrepeatVo> queryDefaultRepeatPage(Map<String, Object> map);
	public Long queryDefaultRepeatCountPage(Map<String, Object> map);
}
