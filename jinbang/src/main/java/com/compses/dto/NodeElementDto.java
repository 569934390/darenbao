package com.compses.dto;

public class NodeElementDto {
	private Long nodeId;
    private Integer nodeTypeId;
    private String symbolName1;
    private String topoTypeDisplayName;
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getNodeTypeId() {
		return nodeTypeId;
	}
	public void setNodeTypeId(Integer nodeTypeId) {
		this.nodeTypeId = nodeTypeId;
	}
	public String getSymbolName1() {
		return symbolName1;
	}
	public void setSymbolName1(String symbolName1) {
		this.symbolName1 = symbolName1;
	}
	public String getTopoTypeDisplayName() {
		return topoTypeDisplayName;
	}
	public void setTopoTypeDisplayName(String topoTypeDisplayName) {
		this.topoTypeDisplayName = topoTypeDisplayName;
	}

}
