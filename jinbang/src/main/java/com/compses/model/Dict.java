package com.compses.model;



public class Dict {

    private Integer bizId;
    private String displayField;
    private String valueField;
    private Integer orders;
    private String types;
    private String typeName;
    private String state;
    private Integer pid;
    private String controlType;

    public Integer getBizId(){
        return bizId;
    }
    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }
    public String getDisplayField(){
        return displayField;
    }
    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }
    public String getValueField(){
        return valueField;
    }
    public void setValueField(String valueField) {
        this.valueField = valueField;
    }
    public Integer getOrders(){
        return orders;
    }
    public void setOrders(Integer orders) {
        this.orders = orders;
    }
    public String getTypes(){
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }
    public String getState(){
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Integer getPid(){
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
