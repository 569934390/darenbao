package com.compses.dto;

import java.util.List;

public class AdvancedTreeNode<T> {
	private String id; 				//节点id
	private String text;			//节点显示内容
	private String icon;           //图标 
	private String iconCls;           //图标 
	private boolean leaf;         //是否叶子 
	private String href;          //链接 

    private String hrefTarget;    //链接指向 

    private boolean expandable=true;   //是否展开 
//    private boolean checked=false;   //是否展开 

    private String description;   //描述信息 

    private Boolean checked;
    
    private boolean expanded;//是否自动展开
    
    private T attribute;
    
	private List<T> children;


    public String getIconCls() {
    	return iconCls;
    }
    public void setIconCls(String iconCls) {
    	this.iconCls = iconCls;
    }
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	public boolean isExpandable() {
		return expandable;
	}
	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}
//	public boolean isChecked() {
//		return checked;
//	}
//	public void setChecked(boolean checked) {
//		this.checked = checked;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public T getAttribute() {
		return attribute;
	}
	public void setAttribute(T attribute) {
		this.attribute = attribute;
	}
	
	
	
}
