package com.club.web.common.db.po;

import org.apache.commons.lang.StringUtils;
import com.club.framework.dto.AbstractDto;

public class CodeValuePO extends AbstractDto{
	private String  codeType;
	private String  codeName;
	private String  codeValue;
	private String  state;
    public String getCodeType() {
        return StringUtils.isBlank(codeType) ? codeType : codeType.trim();
    }
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
    
    public String getCodeName() {
        return StringUtils.isBlank(codeName) ? codeName : codeName.trim();
    }
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    
    public String getCodeValue() {
        return StringUtils.isBlank(codeValue) ? codeValue : codeValue.trim();
    }
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    
    public String getState() {
        return StringUtils.isBlank(state) ? state : state.trim();
    }
    public void setState(String state) {
        this.state = state;
    }
    
}