package com.club.web.store.dao.extend;

import java.util.Map;

import com.club.web.store.dao.base.TimeCycleMapper;
import com.club.web.store.vo.TimeCycleVo;

public interface TimeCycleExtendMapper extends TimeCycleMapper{

   TimeCycleVo findVoByMap(Map<String, Object> map);
   
}