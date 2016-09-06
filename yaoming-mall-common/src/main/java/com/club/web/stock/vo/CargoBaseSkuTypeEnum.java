package com.club.web.stock.vo;

/**
* @Title: CargoBaseSkuTypeEnum.java 
* @Package com.club.web.stock.vo
* @Description: 规格类型下拉框枚举类
* @author hqLin
* @date 2016/03/19
* @version V1.0
 */

public enum CargoBaseSkuTypeEnum {
	a("全部"),
	b("文本"),
	c("值"),
	d("图");
	
	private final String str;
	private CargoBaseSkuTypeEnum(String day) {
		this.str = day;
	}
	
	public static String getTxt(int t){
		return CargoBaseSkuTypeEnum.values()[t].str;
	}
}
