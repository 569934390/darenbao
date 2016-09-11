package com.club.web.client.vo;


import com.club.web.common.vo.BaseVo;

import java.util.Date;
import java.util.List;

/**
 会员表 Date:2016-07-25 20:57:06
 基础字段
 **/
public class MobileUserInfoVo extends BaseVo{

    /**id**/
    private Long userId;
    /**员工录名**/
    private String loginName;
    /**密码**/
    private String password;
    /**真名**/
    private String realName;
    /**  性别 -1:女， 0：未知  1：男  默认：0**/
    private Integer gender;
    /**头像**/
    private String portrait;
    /**手机号**/
    private String mobile;
    /**工号**/
    private String workNumber;
    /**所在省份**/
    private String nativeProvince;
    /**所在省份名字**/
    private String nativeProvinceName;
    /**所在城市**/
    private String nativeCity;
    /**所在城市名字**/
    private String nativeCityName;
    /**所在区域**/
    private String nativeArea;
    /**所在区域名称**/
    private String nativeAreaName;
    /**员工经度**/
    private double staffLon;
    /**身份证**/
    private String idCard;
    /**员纬度**/
    private double staffLat;
    /**创建时间**/
    private Date createTime;
    /**备注**/
    private String remark;
    /**认证时间**/
    private String authenticationTime;
    /**邀请码**/
    private String invitationCode;
    /****/
    private String funcflag;
    /**资金汇总账号**/
    private String supacctid;
    /**交易网会员代码**/
    private String thirdcustid;
    /**会员属性**/
    private String custproperty;
    /**用户昵称**/
    private String nickname;
    /**手机号码**/
    private String mobilephone;
    /**邮箱**/
    private String email;
    /**保留域**/
    private String reserve;
    /**二维码**/
    private String qrCode;
    /**token**/
    private String token;
    /**用户图片**/
    private String photos;
    /**忙贤状态**/
    private String isBusy;      //0：空闲；1：忙碌
    /**生日**/
    private Date birth;
    /**上级用户id**/
    private Long parentUserId;
    /**品牌运营官id**/
    private String brandAgentId;
    //接单状态     0:不接单；1：接单
    private String pickState;

    /*===============================非数据库字段=======================================*/
    //今日可接订单
    private int acceptNum;
    //接单数
    private Integer pickOrderNum;
    //收藏数
    private Integer favouriteNum;
    /**图片列表**/
    private List<String> photoInfoList;
    //服务项目数
    private Integer serviceItemNum;
    //查询起始时间
    private Date searchBeginDate;
    //查询结束时间
    private Date searchEndDate;
    /**转账银行卡户名**/
    private String transferBankUserName;
    /**转账银行卡开户银行**/
    private String transferBankCardName;
    /**转账银行卡**/
    private String transferBankCard;
    //余额
    private double balance;
    /**已结算金额**/
    private double settlementedMoney;
    /**待结算金额**/
    private double toSettlementMoney;

    private int isFavourite;

    private List<MobileUserInfoVo> firstSubordinates;

    private List<MobileUserInfoVo> secondSubordinates;

    private List<MobileUserInfoVo> thirdSubordinates;

    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getLoginName(){
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRealName(){
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public Integer getGender(){
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public String getPortrait(){
        return portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getWorkNumber(){
        return workNumber;
    }
    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }
    public String getNativeProvinceName(){
        return nativeProvinceName;
    }
    public void setNativeProvinceName(String nativeProvinceName) {
        this.nativeProvinceName = nativeProvinceName;
    }
    public String getNativeArea(){
        return nativeArea;
    }
    public void setNativeArea(String nativeArea) {
        this.nativeArea = nativeArea;
    }
    public String getNativeAreaName(){
        return nativeAreaName;
    }
    public void setNativeAreaName(String nativeAreaName) {
        this.nativeAreaName = nativeAreaName;
    }
    public double getStaffLon(){
        return staffLon;
    }
    public void setStaffLon(double staffLon) {
        this.staffLon = staffLon;
    }
    public double getStaffLat(){
        return staffLat;
    }
    public void setStaffLat(double staffLat) {
        this.staffLat = staffLat;
    }
    public String getInvitationCode(){
        return invitationCode;
    }
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
    public String getFuncflag(){
        return funcflag;
    }
    public void setFuncflag(String funcflag) {
        this.funcflag = funcflag;
    }
    public String getSupacctid(){
        return supacctid;
    }
    public void setSupacctid(String supacctid) {
        this.supacctid = supacctid;
    }
    public String getThirdcustid(){
        return thirdcustid;
    }
    public void setThirdcustid(String thirdcustid) {
        this.thirdcustid = thirdcustid;
    }
    public String getCustproperty(){
        return custproperty;
    }
    public void setCustproperty(String custproperty) {
        this.custproperty = custproperty;
    }
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getMobilephone(){
        return mobilephone;
    }
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getReserve(){
        return reserve;
    }
    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
    public String getQrCode(){
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    public String getToken(){
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(String isBusy) {
        this.isBusy = isBusy;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<String> getPhotoInfoList() {
        return photoInfoList;
    }

    public void setPhotoInfoList(List<String> photoInfoList) {
        this.photoInfoList = photoInfoList;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getBrandAgentId() {
        return brandAgentId;
    }

    public void setBrandAgentId(String brandAgentId) {
        this.brandAgentId = brandAgentId;
    }

    public Integer getServiceItemNum() {
        return serviceItemNum;
    }

    public void setServiceItemNum(Integer serviceItemNum) {
        this.serviceItemNum = serviceItemNum;
    }

    public Integer getPickOrderNum() {
        return pickOrderNum;
    }

    public void setPickOrderNum(Integer pickOrderNum) {
        this.pickOrderNum = pickOrderNum;
    }

    public Integer getFavouriteNum() {
        return favouriteNum;
    }

    public void setFavouriteNum(Integer favouriteNum) {
        this.favouriteNum = favouriteNum;
    }

    public Date getSearchBeginDate() {
        return searchBeginDate;
    }

    public void setSearchBeginDate(Date searchBeginDate) {
        this.searchBeginDate = searchBeginDate;
    }

    public Date getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(Date searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public int getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(int acceptNum) {
        this.acceptNum = acceptNum;
    }

    public String getPickState() {
        return pickState;
    }

    public void setPickState(String pickState) {
        this.pickState = pickState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthenticationTime() {
        return authenticationTime;
    }

    public void setAuthenticationTime(String authenticationTime) {
        this.authenticationTime = authenticationTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNativeCityName() {
        return nativeCityName;
    }

    public void setNativeCityName(String nativeCityName) {
        this.nativeCityName = nativeCityName;
    }

    public String getTransferBankUserName() {
        return transferBankUserName;
    }

    public void setTransferBankUserName(String transferBankUserName) {
        this.transferBankUserName = transferBankUserName;
    }

    public String getTransferBankCardName() {
        return transferBankCardName;
    }

    public void setTransferBankCardName(String transferBankCardName) {
        this.transferBankCardName = transferBankCardName;
    }

    public String getTransferBankCard() {
        return transferBankCard;
    }

    public void setTransferBankCard(String transferBankCard) {
        this.transferBankCard = transferBankCard;
    }

    public List<MobileUserInfoVo> getFirstSubordinates() {
        return firstSubordinates;
    }

    public void setFirstSubordinates(List<MobileUserInfoVo> firstSubordinates) {
        this.firstSubordinates = firstSubordinates;
    }

    public List<MobileUserInfoVo> getSecondSubordinates() {
        return secondSubordinates;
    }

    public void setSecondSubordinates(List<MobileUserInfoVo> secondSubordinates) {
        this.secondSubordinates = secondSubordinates;
    }

    public List<MobileUserInfoVo> getThirdSubordinates() {
        return thirdSubordinates;
    }

    public void setThirdSubordinates(List<MobileUserInfoVo> thirdSubordinates) {
        this.thirdSubordinates = thirdSubordinates;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getSettlementedMoney() {
        return settlementedMoney;
    }

    public void setSettlementedMoney(double settlementedMoney) {
        this.settlementedMoney = settlementedMoney;
    }

    public double getToSettlementMoney() {
        return toSettlementMoney;
    }

    public void setToSettlementMoney(double toSettlementMoney) {
        this.toSettlementMoney = toSettlementMoney;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getNativeProvince() {
        return nativeProvince;
    }

    public void setNativeProvince(String nativeProvince) {
        this.nativeProvince = nativeProvince;
    }

    public String getNativeCity() {
        return nativeCity;
    }

    public void setNativeCity(String nativeCity) {
        this.nativeCity = nativeCity;
    }
}
