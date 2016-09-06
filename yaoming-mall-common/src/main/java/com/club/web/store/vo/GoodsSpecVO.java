package com.club.web.store.vo;

import java.util.List;

public class GoodsSpecVO {
	private String name;
	private List<String> dataList;
	
	public GoodsSpecVO(String name, List<String> dataList) {
		this.name = name;
		this.dataList = dataList;
	}
	
	public String getName() {
		return name;
	}
	public List<String> getDataList() {
		return dataList;
	}
	
}
