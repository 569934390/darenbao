package com.club.web.weixin.service;

import java.util.List;
import java.util.Map;

import com.club.web.weixin.vo.WinXinEvaluateVo;

public interface GoodWeixinService {
	
	Map<String, Object> saveEvaluation(List<WinXinEvaluateVo> jsonArr);
}