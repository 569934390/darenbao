package com.club.web.module.vo;

import java.util.Date;

import com.club.framework.util.StringUtils;
import com.club.web.module.constant.OpinionType;
import com.club.web.util.Html2Text;
/**
 * 意见反馈Vo
 * @author zhuzd
 *
 */
public class OpinionVo {
	private String id;

    private String clientId;
    
    private String clientName;

    private String clientPhone;
    
    private Integer platform = 0;

    private String platformText;
    
    private String versionName;

    private String type;

    private Date createTime;

    private String description;
            
    public String getClientName() {
        return StringUtils.isEmpty(this.clientName)?"游客":this.clientName;
    }
    
    public Integer getPlatform() {
		return platform;
	}

	public String getPlatformText() {
		return platformText;
	}

	public void setPlatformText(String platformText) {
		this.platformText = platformText;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    public String getClientPhone() {
        return clientPhone;
    }	

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone == null ? null : clientPhone.trim();
    }
	    
    public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getTypeName(){
		return this.type != null?OpinionType.getTextByDbData(Integer.valueOf(this.type)):"";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }
    
    public String getDescriptionText() {
		return this.description != null ? Html2Text.getInstance().convert(this.description):"";
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}
