package com.compses.model;


import java.util.Date;

/**
 举报表 Date:2016-07-28 01:24:16
 **/
public class ReportInfo{

    /**举报id**/
    private String reportId;
    /**举报者id**/
    private String reporterId;
    /**举报者姓名**/
    private String reporterName;
    /**举报者类型**/
    private String reporterType;
    /**举报者手机号**/
    private String reporterMobile;
    /**被举报对象id**/
    private String beReporterId;
    /**被举对象姓名**/
    private String beReporterName;
    /**举报描述**/
    private String description;
    /**举报时间**/
    private Date reportDate;
    /**操作类型**/
    private String operationType;
    /**备注**/
    private String remark;

    public String getReportId(){
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public String getReporterId(){
        return reporterId;
    }
    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }
    public String getReporterName(){
        return reporterName;
    }
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
    public String getReporterType(){
        return reporterType;
    }
    public void setReporterType(String reporterType) {
        this.reporterType = reporterType;
    }
    public String getReporterMobile(){
        return reporterMobile;
    }
    public void setReporterMobile(String reporterMobile) {
        this.reporterMobile = reporterMobile;
    }
    public String getBeReporterId(){
        return beReporterId;
    }
    public void setBeReporterId(String beReporterId) {
        this.beReporterId = beReporterId;
    }
    public String getBeReporterName(){
        return beReporterName;
    }
    public void setBeReporterName(String beReporterName) {
        this.beReporterName = beReporterName;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getReportDate(){
        return reportDate;
    }
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
    public String getOperationType(){
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
