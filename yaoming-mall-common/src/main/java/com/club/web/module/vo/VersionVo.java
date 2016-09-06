package com.club.web.module.vo;

import java.util.Date;

import com.club.framework.util.StringUtils;
import com.club.web.module.constant.PlatformType;
import com.club.web.module.constant.VersionDownloadWay;
import com.club.web.module.constant.VersionStatus;
import com.club.web.util.Html2Text;
/**
 * 版本管理Vo
 * @author zhuzd
 *
 */
public class VersionVo {

	private String id;

	private Integer code;

    private String name;

    private String modifier;

    private String creater;

    private Date updateTime;

    private String url;

    private Integer platform;

    private Integer status;

    private Integer effect;

    private String description;
    
    private Integer downloadWay = VersionDownloadWay.非强制下载.getDbData();
    
    public Integer getDownloadWay() {
		return downloadWay;
	}

	public void setDownloadWay(Integer downloadWay) {
		this.downloadWay = downloadWay;
	}

	public String getDownloadWayText(){
		if(this.downloadWay != null){
			return VersionDownloadWay.getTextByDbData(this.downloadWay);
		}
		return "";
	}
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

   
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusText(){
		if(this.status != null){
			return VersionStatus.getTextByDbData(this.status);
		}
		return "";
	}
	
    public Integer getEffect() {
		return effect;
	}

	public void setEffect(Integer effect) {
		this.effect = effect;
	}
	
	public String getEffectText(){
		return this.effect != null && this.effect == 1?"YES":"NO";
	}

	public String getDescription() {
        return description;
    }
	
	public String getDescriptionText(){
		return this.description != null ? Html2Text.getInstance().convert(this.description):"";
	}

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    
    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getPlatformText() {
		if(this.platform != null){
			return PlatformType.getTextByDbData(this.platform);
		}
		return "";
	}
    
    public String getUrlText(){
    	String text = "";
    	if(StringUtils.isNotEmpty(this.url)){
    		text = "<a href="+this.url+" target='_blank'>下载链接</a>";
    	}
    	return text;
    }
}
