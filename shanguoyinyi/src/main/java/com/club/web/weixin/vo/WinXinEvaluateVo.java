package com.club.web.weixin.vo;

import com.club.web.store.vo.GoodEvaluationVo;

/**
 * C端评论信息
 * 
 * @author hqLin
 * @add by 2016-07-22
 */
public class WinXinEvaluateVo {
	private GoodEvaluationVo evaluate;	//评论信息
	private String[] logo1;	//上传图片

	public GoodEvaluationVo getEvaluate() {
		return evaluate;
	}
	
	public void setEvaluate(GoodEvaluationVo evaluate) {
		this.evaluate = evaluate;
	}

	public String[] getLogo1() {
		return logo1;
	}

	public void setLogo1(String[] logo1) {
		this.logo1 = logo1;
	}
}