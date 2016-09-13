package com.compses.dto;

/**
 * ' 节点类型的java类
 * 
 * @author jianghw
 * 
 */
public class NodeType {
	// 设备类型id
	private Integer nodeTypeId;
	// 设备类型名称
	private String nodeTypeName;
	// 设备systemobjectid
	private String systemObjectId;
	// IP属性
	private String nodeTypeAttr;
	
	private Integer nodeId;

	public Integer getNodeTypeId() {
		return nodeTypeId;
	}

	public void setNodeTypeId(Integer nodeTypeId) {
		this.nodeTypeId = nodeTypeId;
	}

	public String getNodeTypeName() {
		return nodeTypeName;
	}

	public void setNodeTypeName(String nodeTypeName) {
		this.nodeTypeName = nodeTypeName;
	}

	public String getSystemObjectId() {
		return systemObjectId;
	}

	public void setSystemObjectId(String systemObjectId) {
		this.systemObjectId = systemObjectId;
	}
	

	public String getNodeTypeAttr() {
		return nodeTypeAttr;
	}

	public void setNodeTypeAttr(String nodeTypeAttr) {
		this.nodeTypeAttr = nodeTypeAttr;
	}
	

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public NodeType() {
		super();
	}

}
