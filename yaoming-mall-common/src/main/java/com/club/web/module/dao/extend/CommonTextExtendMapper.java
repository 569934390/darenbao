package com.club.web.module.dao.extend;

import com.club.web.module.dao.base.CommonTextMapper;
import com.club.web.module.dao.base.po.CommonText;
/**
 * 通用文本Dao扩展类
 * @author zhuzd
 *
 */
public interface CommonTextExtendMapper extends CommonTextMapper{

	CommonText findTextByType(int type);
    
	
}