package com.compses.model;

import java.util.List;

public class DopChart {
	private Integer chartId;
	private String type;
	private String title;
	private String subTitle;
	private Integer sort;
	private String state;
	private Integer sourceCount;
	private String adviceConfig;
	private List<DopChartDetail> chartDetails;
	private DopChartDetail xs;
	public Integer getChartId() {
		return chartId;
	}
	public void setChartId(Integer chartId) {
		this.chartId = chartId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getSourceCount() {
		return sourceCount;
	}
	public void setSourceCount(Integer sourceCount) {
		this.sourceCount = sourceCount;
	}
	public String getAdviceConfig() {
		return adviceConfig;
	}
	public void setAdviceConfig(String adviceConfig) {
		this.adviceConfig = adviceConfig;
	}
	public List<DopChartDetail> getChartDetails() {
		return chartDetails;
	}
	public void setChartDetails(List<DopChartDetail> chartDetails) {
		this.chartDetails = chartDetails;
	}
	public DopChartDetail getXs() {
		return xs;
	}
	public void setXs(DopChartDetail xs) {
		this.xs = xs;
	}
	
}
	
