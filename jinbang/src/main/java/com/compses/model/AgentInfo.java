package com.compses.model;


import java.util.Date;

/**
 运营商表 Date:2016-08-02 17:02:24
 **/
public class AgentInfo{

    /**主键**/
    private String agentId;
    /**运营商名称**/
    private String agentName;
    /**运营商编号**/
    private String agentCode;
    /**创建时间**/
    private Date createDate;
    /**运营商类型**/
    private String agentCategory;
    /**负责人名称**/
    private String responsiblePerson;
    /**联系电话**/
    private String mobile;
    /**所在省id**/
    private Integer nativeProvinceId;
    /**所在省名称**/
    private String nativeProvinceName;
    /**所在市id**/
    private String nativeCityId;
    /**所在市名称**/
    private String nativeCityName;
    /**所在区id**/
    private String nativeAreaId;
    /**所在区名称**/
    private String nativeAreaName;
    /**地址**/
    private String location;
    /**审核状态**/
    private String reviewStatus;
    /**身份证号码**/
    private String idCard;
    /**虚拟卡号码**/
    private String virtualCard;
    /**收入总额**/
    private double totalIncome;
    /**可转出金额**/
    private double turnoutMoney;
    /**已结算金额**/
    private double settleMoney;
    /**待结算金额**/
    private double staySettleMoney;
    /**注册号**/
    private String regNumber;
    /**营业执照**/
    private String businessLicense;
    /**银行卡类别**/
    private String visaCategory;
    /**银行类别**/
    private String bankCategory;
    /**银行卡卡号**/
    private String visaNumber;
    /**上级运营商id**/
    private String parentAgentId;

    public String getAgentId(){
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getAgentName(){
        return agentName;
    }
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    public String getAgentCode(){
        return agentCode;
    }
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    public String getAgentCategory(){
        return agentCategory;
    }
    public void setAgentCategory(String agentCategory) {
        this.agentCategory = agentCategory;
    }
    public String getResponsiblePerson(){
        return responsiblePerson;
    }
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Integer getNativeProvinceId(){
        return nativeProvinceId;
    }
    public void setNativeProvinceId(Integer nativeProvinceId) {
        this.nativeProvinceId = nativeProvinceId;
    }
    public String getNativeProvinceName(){
        return nativeProvinceName;
    }
    public void setNativeProvinceName(String nativeProvinceName) {
        this.nativeProvinceName = nativeProvinceName;
    }
    public String getNativeCityName(){
        return nativeCityName;
    }
    public void setNativeCityName(String nativeCityName) {
        this.nativeCityName = nativeCityName;
    }
    public String getNativeAreaName(){
        return nativeAreaName;
    }
    public void setNativeAreaName(String nativeAreaName) {
        this.nativeAreaName = nativeAreaName;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getReviewStatus(){
        return reviewStatus;
    }
    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
    public String getIdCard(){
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getVirtualCard(){
        return virtualCard;
    }
    public void setVirtualCard(String virtualCard) {
        this.virtualCard = virtualCard;
    }
    public String getRegNumber(){
        return regNumber;
    }
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }
    public String getBusinessLicense(){
        return businessLicense;
    }
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
    public String getVisaCategory(){
        return visaCategory;
    }
    public void setVisaCategory(String visaCategory) {
        this.visaCategory = visaCategory;
    }
    public String getBankCategory(){
        return bankCategory;
    }
    public void setBankCategory(String bankCategory) {
        this.bankCategory = bankCategory;
    }
    public String getVisaNumber(){
        return visaNumber;
    }
    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }
    public String getParentAgentId(){
        return parentAgentId;
    }
    public void setParentAgentId(String parentAgentId) {
        this.parentAgentId = parentAgentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTurnoutMoney() {
        return turnoutMoney;
    }

    public void setTurnoutMoney(double turnoutMoney) {
        this.turnoutMoney = turnoutMoney;
    }

    public double getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(double settleMoney) {
        this.settleMoney = settleMoney;
    }

    public double getStaySettleMoney() {
        return staySettleMoney;
    }

    public void setStaySettleMoney(double staySettleMoney) {
        this.staySettleMoney = staySettleMoney;
    }

    public String getNativeCityId() {
        return nativeCityId;
    }

    public void setNativeCityId(String nativeCityId) {
        this.nativeCityId = nativeCityId;
    }

    public String getNativeAreaId() {
        return nativeAreaId;
    }

    public void setNativeAreaId(String nativeAreaId) {
        this.nativeAreaId = nativeAreaId;
    }
}
