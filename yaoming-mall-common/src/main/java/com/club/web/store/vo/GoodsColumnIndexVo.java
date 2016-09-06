package com.club.web.store.vo;

/**   
* @Title: GoodsColumnVo.java
* @Package com.club.web.store.vo 
* @Description: 手机端首页商品基础栏目VO 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public class GoodsColumnIndexVo {
    private String id;

    private String showpicture;
    
    private String columnName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShowpicture() {
		return showpicture;
	}

	public void setShowpicture(String showpicture) {
		this.showpicture = showpicture;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}