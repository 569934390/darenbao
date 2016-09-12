package com.compses.dto;

import com.compses.model.MobileUserInfo;

/**
 * Created by Administrator on 2016/8/5 0005.
 * 手机用户扩展字段
 */
public class MobileUserInfoDto extends MobileUserInfo{

    //用户id
    private String userId;
    /**面试人**/
    private String interviewer;
    /**身份证照片**/
    private String idCardPhotos;
    /**紧急联系人**/
    private String emergencyContact;
    /**紧急联系人关系**/
    private String emergencyContactRelationship;
    /**紧急联系人手机**/
    private String emergencyContactMobilephone;
    /**转账银行卡户名**/
    private String transferBankUserName;
    /**转账银行卡开户银行**/
    private String transferBankCardName;
    /**转账银行卡**/
    private String transferBankCard;
    /**自动解除时间**/
    private String autoRelieveTime;
    /**修改时间**/
    private String updateTime;
    /**限制时间**/
    private String forbidTime;
    /**考试时间**/
    private String examTime;
    /**签约时间**/
    private String signedTime;
    /**限制状态 0:表示正常 1：表示限制**/
    private Integer forbidState;
    /**技能拥有开始时间 **/
    private String skillTime;
    /**技能年限(以月份为单位)**/
    private Integer skillAge;
    /**技能**/
    private String skill;
    /**服务积分**/
    private Integer serviceIntegral;
    /**用户推广用户端量**/
    private Integer userExtendNumber;
    /**员工推广员工端量**/
    private Integer staffExtendNumber;
    /**我的图片张数**/
    private Integer imageNumber;
    /**审核内容**/
    private String approveContent;
    /**推荐人**/
    private String recommendStaffId;
    /**招募开始时间**/
    private String invitationStartTime;
    /**招募结束时间**/
    private String invitationEndTime;
    /**招募人数**/
    private Integer invitationNumber;
    /**  招募状态 0:表示未招募过 1：表示招募中，2：表示已经招募过**/
    private Integer invitationState;
    /**员工类别 0:表示普通员工 1:表示公司员工**/
    private Integer staffCatalog;
    /**接单类别 1:接收广播  2:不接收广播**/
    private Integer receiveCatalog;
    /**收款清算行行号**/
    private String transferBankCardCode;
    /**我的技能认证照片**/
    private String skillImages;
    /**我的认证视频**/
    private String skillVideo;
    /**合作伙伴地区的个数**/
    private Integer areaNumber;
    /**给用户介绍自己30个字**/
    private String skillTerm;
    /**帮助者是否是地区管理者 0：表示不是 1：表示是**/
    private Integer areaState;
    /**注册设备ID**/
    private String deviceId;
    /**代理商ID**/
    private String agentId;
    /**常住地址详细**/
    private String residentAddres;
    /**员工状态 0:初始注册 1：考试通过  2：人工审核通过，只有状态为2方可上岗接单,并且当限制接单字段（规则……）
     员工状态 0:初始注册 1：考试通过 2：人工审核通过，只有状态为2方可上岗接单 ('限制类型',
     // 2:随时，3：1周 4：1个月， 5：半年 6：1年 999：永久)**/
    private Integer status;

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public String getIdCardPhotos() {
        return idCardPhotos;
    }

    public void setIdCardPhotos(String idCardPhotos) {
        this.idCardPhotos = idCardPhotos;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getEmergencyContactMobilephone() {
        return emergencyContactMobilephone;
    }

    public void setEmergencyContactMobilephone(String emergencyContactMobilephone) {
        this.emergencyContactMobilephone = emergencyContactMobilephone;
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

    public String getAutoRelieveTime() {
        return autoRelieveTime;
    }

    public void setAutoRelieveTime(String autoRelieveTime) {
        this.autoRelieveTime = autoRelieveTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getForbidTime() {
        return forbidTime;
    }

    public void setForbidTime(String forbidTime) {
        this.forbidTime = forbidTime;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(String signedTime) {
        this.signedTime = signedTime;
    }

    public Integer getForbidState() {
        return forbidState;
    }

    public void setForbidState(Integer forbidState) {
        this.forbidState = forbidState;
    }

    public String getSkillTime() {
        return skillTime;
    }

    public void setSkillTime(String skillTime) {
        this.skillTime = skillTime;
    }

    public Integer getSkillAge() {
        return skillAge;
    }

    public void setSkillAge(Integer skillAge) {
        this.skillAge = skillAge;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getServiceIntegral() {
        return serviceIntegral;
    }

    public void setServiceIntegral(Integer serviceIntegral) {
        this.serviceIntegral = serviceIntegral;
    }

    public Integer getUserExtendNumber() {
        return userExtendNumber;
    }

    public void setUserExtendNumber(Integer userExtendNumber) {
        this.userExtendNumber = userExtendNumber;
    }

    public Integer getStaffExtendNumber() {
        return staffExtendNumber;
    }

    public void setStaffExtendNumber(Integer staffExtendNumber) {
        this.staffExtendNumber = staffExtendNumber;
    }

    public Integer getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(Integer imageNumber) {
        this.imageNumber = imageNumber;
    }

    public String getApproveContent() {
        return approveContent;
    }

    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public String getRecommendStaffId() {
        return recommendStaffId;
    }

    public void setRecommendStaffId(String recommendStaffId) {
        this.recommendStaffId = recommendStaffId;
    }

    public String getInvitationStartTime() {
        return invitationStartTime;
    }

    public void setInvitationStartTime(String invitationStartTime) {
        this.invitationStartTime = invitationStartTime;
    }

    public String getInvitationEndTime() {
        return invitationEndTime;
    }

    public void setInvitationEndTime(String invitationEndTime) {
        this.invitationEndTime = invitationEndTime;
    }

    public Integer getInvitationNumber() {
        return invitationNumber;
    }

    public void setInvitationNumber(Integer invitationNumber) {
        this.invitationNumber = invitationNumber;
    }

    public Integer getInvitationState() {
        return invitationState;
    }

    public void setInvitationState(Integer invitationState) {
        this.invitationState = invitationState;
    }

    public Integer getStaffCatalog() {
        return staffCatalog;
    }

    public void setStaffCatalog(Integer staffCatalog) {
        this.staffCatalog = staffCatalog;
    }

    public Integer getReceiveCatalog() {
        return receiveCatalog;
    }

    public void setReceiveCatalog(Integer receiveCatalog) {
        this.receiveCatalog = receiveCatalog;
    }

    public String getTransferBankCardCode() {
        return transferBankCardCode;
    }

    public void setTransferBankCardCode(String transferBankCardCode) {
        this.transferBankCardCode = transferBankCardCode;
    }

    public String getSkillImages() {
        return skillImages;
    }

    public void setSkillImages(String skillImages) {
        this.skillImages = skillImages;
    }

    public String getSkillVideo() {
        return skillVideo;
    }

    public void setSkillVideo(String skillVideo) {
        this.skillVideo = skillVideo;
    }

    public Integer getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(Integer areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getSkillTerm() {
        return skillTerm;
    }

    public void setSkillTerm(String skillTerm) {
        this.skillTerm = skillTerm;
    }

    public Integer getAreaState() {
        return areaState;
    }

    public void setAreaState(Integer areaState) {
        this.areaState = areaState;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getResidentAddres() {
        return residentAddres;
    }

    public void setResidentAddres(String residentAddres) {
        this.residentAddres = residentAddres;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
