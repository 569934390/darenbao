package com.compses.dto;


public class TreeParam {
	private String rootId;          	//根节点ID 
	private String sqlKey; 				//获取数据的SQL名称
	private String sqlValue;			//可直接提供sql供查询，一般树有多层是没有写死(预留)
	private String idKey;				//树节点查询子节点值关联KEY
	private String nodeId; 				//节点id KEY
	private String nodeName;           	//节点text KEY
	private String iconClass;          	//图标样式处理接口 
	private String iconAble;          	//是否显示图标 
	private String nodeLeaf;           	//节点icon KEY(String)  (onlyLeafCheckable : true 时必填,否则无法判断为末节点)
	private String nodeIcon;         	//节点icon KEY(String)  (iconable : true 时必填,否则图标)
	private String paramMap;          	//链接 
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	public String getSqlKey() {
		return sqlKey;
	}
	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}
	public String getSqlValue() {
		return sqlValue;
	}
	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}
	public String getIdKey() {
		return idKey;
	}
	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	public String getIconAble() {
		return iconAble;
	}
	public void setIconAble(String iconAble) {
		this.iconAble = iconAble;
	}
	public String getNodeIcon() {
		return nodeIcon;
	}
	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}
	public String getNodeLeaf() {
		return nodeLeaf;
	}
	public void setNodeLeaf(String nodeLeaf) {
		this.nodeLeaf = nodeLeaf;
	}
	public String getParamMap() {
		return paramMap;
	}
	public void setParamMap(String paramMap) {
		this.paramMap = paramMap;
	}
}
