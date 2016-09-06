package com.club.web.stock.vo;

import java.util.List;

/**
 * 前端的树下拉菜单
 * 
 * @author wqh
 * @add by 2016-04-05
 */
public class TreeListVo {
	private String id;
	private String pId;
	private String name;
	private List<TreeListVo> children;
	private boolean open;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeListVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeListVo> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

}
