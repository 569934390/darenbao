package com.club.web.weixin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.StringUtils;
import com.club.web.deal.service.IndentService;
import com.club.web.deal.vo.IndentVo;
import com.club.web.store.service.GoodEvaluationService;
import com.club.web.store.vo.GoodEvaluationVo;
import com.club.web.weixin.service.GoodWeixinService;
import com.club.web.weixin.vo.WinXinEvaluateVo;

@Transactional
@Service("goodWeixinService")
public class GoodWeixinServiceImlp implements GoodWeixinService {

	@Autowired
	private GoodEvaluationService evaluationService;
	@Autowired
	IndentService indentService;

	/**
	 * 保存商品评价
	 */
	@Override
	public Map<String, Object> saveEvaluation(List<WinXinEvaluateVo> jsonArray) {
		Map<String, Object> result = new HashMap<String, Object>();
		GoodEvaluationVo goodEvaluationVo = new GoodEvaluationVo();
		for(int i=0; i<jsonArray.size(); i++){
			goodEvaluationVo = new GoodEvaluationVo();
			WinXinEvaluateVo winXinEvaluateVo = jsonArray.get(i);
			goodEvaluationVo = winXinEvaluateVo.getEvaluate();
			try {
				evaluationService.saveEvaluation(goodEvaluationVo, winXinEvaluateVo.getLogo1(), 1);
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "操作失败！");
				return result;
			}
		}
		try {
			if(goodEvaluationVo.getIndentId() != null && StringUtils.isNotEmpty(goodEvaluationVo.getIndentId())){
				IndentVo indentVo = indentService.findVoById(Long.valueOf(goodEvaluationVo.getIndentId()));
				if(indentVo.getStatus() == 12){
					indentService.updateStatus(goodEvaluationVo.getIndentId(), "done", null, new HashMap<String, Object>());					
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "操作失败！");
			return result;
		}
		
		return result;
	}
}