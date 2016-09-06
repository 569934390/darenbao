package com.club.web.stock.vo;

import java.util.List;

/**
 * 货品分类手机端Vo
 * @author zhuzd
 *
 */
public class CargoClassifyAppVo {
	
	private String classify;
	
    private String text;
    
    private String imgUrl;
    
    private List<CargoClassifyAppVo> childrens;
    
	public List<CargoClassifyAppVo> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<CargoClassifyAppVo> childrens) {
		this.childrens = childrens;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
    
}
