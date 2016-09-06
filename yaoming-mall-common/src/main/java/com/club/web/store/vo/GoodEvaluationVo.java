package com.club.web.store.vo;

import java.util.Date;
import java.util.List;

public class GoodEvaluationVo {
	private String id;

    private String goodSkuid;

    private Long user;

    private String username;

    private String content;

    private String image;

    private Date evaluateTime;

    private Integer score;
    
    private String skuName;
    
    private String photo;
    
    private String indentId;
    
    public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	private List<EvaluationImageVo> imageVoList;
    
    public List<EvaluationImageVo> getImageVoList() {
		return imageVoList;
	}

	public void setImageVoList(List<EvaluationImageVo> imageVoList) {
		this.imageVoList = imageVoList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodSkuid() {
        return goodSkuid;
    }

    public void setGoodSkuid(String goodSkuid) {
        this.goodSkuid = goodSkuid;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIndentId() {
		return indentId;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
}