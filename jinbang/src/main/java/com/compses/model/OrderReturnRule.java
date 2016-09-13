package com.compses.model;



/**
 订单返利规则 Date:2016-08-25 21:01:12
 **/
public class OrderReturnRule{

    /**id**/
    private String returnRuleId;
    /**订单类型**/
    private String orderCategory;
    /**抽比规则**/
    private String ruleContent;

    public String getReturnRuleId() {
        return returnRuleId;
    }

    /*==============================================*/

    private double platformRebate;

    private double channelRebate;
    //买方
    /*中国区运营商*/
    private double sellerChinaZoneAgent;
    /*地区运营商*/
    private double sellerAreaZoneAgent;
    /*站长*/
    private double sellerStationmaster;
    /*品牌运营官*/
    private double sellerBandAgent;
    /*一级分销会员*/
    private double sellerFirstLevel;
    /*二级分销会员*/
    private double sellerSecondLevel;
    /*三级分销会员*/
    private double sellerThirdLevel;

    //卖方
    /*中国区运营商*/
    private double buyerChinaZoneAgent;
    /*地区运营商*/
    private double buyerAreaZoneAgent;
    /*站长*/
    private double buyerStationmaster;
    /*品牌运营官*/
    private double buyerBandAgent;
    /*一级分销会员*/
    private double buyerFirstLevel;
    /*二级分销会员*/
    private double buyerSecondLevel;
    /*三级分销会员*/
    private double buyerThirdLevel;

    public void setReturnRuleId(String returnRuleId) {
        this.returnRuleId = returnRuleId;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public double getPlatformRebate() {
        return platformRebate;
    }

    public void setPlatformRebate(double platformRebate) {
        this.platformRebate = platformRebate;
    }

    public double getChannelRebate() {
        return channelRebate;
    }

    public void setChannelRebate(double channelRebate) {
        this.channelRebate = channelRebate;
    }

    public double getSellerChinaZoneAgent() {
        return sellerChinaZoneAgent;
    }

    public void setSellerChinaZoneAgent(double sellerChinaZoneAgent) {
        this.sellerChinaZoneAgent = sellerChinaZoneAgent;
    }

    public double getSellerAreaZoneAgent() {
        return sellerAreaZoneAgent;
    }

    public void setSellerAreaZoneAgent(double sellerAreaZoneAgent) {
        this.sellerAreaZoneAgent = sellerAreaZoneAgent;
    }

    public double getSellerStationmaster() {
        return sellerStationmaster;
    }

    public void setSellerStationmaster(double sellerStationmaster) {
        this.sellerStationmaster = sellerStationmaster;
    }

    public double getSellerBandAgent() {
        return sellerBandAgent;
    }

    public void setSellerBandAgent(double sellerBandAgent) {
        this.sellerBandAgent = sellerBandAgent;
    }

    public double getSellerFirstLevel() {
        return sellerFirstLevel;
    }

    public void setSellerFirstLevel(double sellerFirstLevel) {
        this.sellerFirstLevel = sellerFirstLevel;
    }

    public double getSellerSecondLevel() {
        return sellerSecondLevel;
    }

    public void setSellerSecondLevel(double sellerSecondLevel) {
        this.sellerSecondLevel = sellerSecondLevel;
    }

    public double getSellerThirdLevel() {
        return sellerThirdLevel;
    }

    public void setSellerThirdLevel(double sellerThirdLevel) {
        this.sellerThirdLevel = sellerThirdLevel;
    }

    public double getBuyerChinaZoneAgent() {
        return buyerChinaZoneAgent;
    }

    public void setBuyerChinaZoneAgent(double buyerChinaZoneAgent) {
        this.buyerChinaZoneAgent = buyerChinaZoneAgent;
    }

    public double getBuyerAreaZoneAgent() {
        return buyerAreaZoneAgent;
    }

    public void setBuyerAreaZoneAgent(double buyerAreaZoneAgent) {
        this.buyerAreaZoneAgent = buyerAreaZoneAgent;
    }

    public double getBuyerStationmaster() {
        return buyerStationmaster;
    }

    public void setBuyerStationmaster(double buyerStationmaster) {
        this.buyerStationmaster = buyerStationmaster;
    }

    public double getBuyerBandAgent() {
        return buyerBandAgent;
    }

    public void setBuyerBandAgent(double buyerBandAgent) {
        this.buyerBandAgent = buyerBandAgent;
    }

    public double getBuyerFirstLevel() {
        return buyerFirstLevel;
    }

    public void setBuyerFirstLevel(double buyerFirstLevel) {
        this.buyerFirstLevel = buyerFirstLevel;
    }

    public double getBuyerSecondLevel() {
        return buyerSecondLevel;
    }

    public void setBuyerSecondLevel(double buyerSecondLevel) {
        this.buyerSecondLevel = buyerSecondLevel;
    }

    public double getBuyerThirdLevel() {
        return buyerThirdLevel;
    }

    public void setBuyerThirdLevel(double buyerThirdLevel) {
        this.buyerThirdLevel = buyerThirdLevel;
    }
}
