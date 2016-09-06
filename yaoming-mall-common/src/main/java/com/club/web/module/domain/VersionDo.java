package com.club.web.module.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.module.constant.VersionEffect;
import com.club.web.module.domain.repository.VersionRepository;
/**
 * 版本管理Do
 * @author zhuzd
 *
 */
@Configurable
public class VersionDo {
	private Long id;

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
    
    private Integer downloadWay;

    @Autowired
    private VersionRepository repository;

    public Integer getDownloadWay() {
		return downloadWay;
	}

	public void setDownloadWay(Integer downloadWay) {
		this.downloadWay = downloadWay;
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 更新生失效状态
     * @param effect
     * @param username
     * @param updateTime
     */
	public void updateEffect(String effect, String username, Date updateTime) {
		this.setEffect(VersionEffect.getDbDataByName(effect));
		this.setModifier(username != null ? username : this.getModifier());
		this.setUpdateTime(updateTime != null ? updateTime : this.getUpdateTime());
		repository.updateEffect(this);
	}

	public void save() {
		repository.add(this);
	}

	public void update() {
		repository.update(this);
	}
	public void delete(){
		repository.deleteById(this.getId());
	}
}
