package com.compses.dto;

import java.util.List;
import java.util.Map;

import com.compses.util.ReportUtils.REPORT_TYPE;

public class ReportRequest {
	private Map<String, Object> reportParameterMap;
	private List<?> dataSet;
	private Enum<REPORT_TYPE> fileType;
	private String templateName;
	private TemplateParameter templateParameter;
	private String reportName;

	
	public ReportRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ReportRequest(Map<String, Object> reportParameterMap,
			List<?> dataSet, Enum<REPORT_TYPE> fileType,
			String templateName, TemplateParameter templateParameter,
			String reportName) {
		super();
		this.reportParameterMap = reportParameterMap;
		this.dataSet = dataSet;
		this.fileType = fileType;
		this.templateName = templateName;
		this.templateParameter = templateParameter;
		this.reportName = reportName;
	}

	public Map<String, Object> getReportParameterMap() {
		return reportParameterMap;
	}


	public void setReportParameterMap(Map<String, Object> reportParameterMap) {
		this.reportParameterMap = reportParameterMap;
	}


	public List<?> getDataSet() {
		return dataSet;
	}

	public void setDataSet(List<?> dataSet) {
		this.dataSet = dataSet;
	}

	public Enum<REPORT_TYPE> getFileType() {
		return fileType;
	}

	public void setFileType(Enum<REPORT_TYPE> fileType) {
		this.fileType = fileType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public TemplateParameter getTemplateParameter() {
		return templateParameter;
	}


	public void setTemplateParameter(TemplateParameter templateParameter) {
		this.templateParameter = templateParameter;
	}



	
	
	
}
