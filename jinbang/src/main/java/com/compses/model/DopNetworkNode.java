package com.compses.model;

import java.util.List;

public class DopNetworkNode {
	private Integer nodeId;
	private String nodeName;
	private String nodeType;
	private String nodeIcon;
	private List<DopChart> children;
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeIcon() {
		return nodeIcon;
	}
	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}
	public List<DopChart> getChildren() {
		return children;
	}
	public void setChildren(List<DopChart> children) {
		this.children = children;
	}
	
}
	
