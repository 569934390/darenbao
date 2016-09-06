package com.club.web.event.vo;

import java.util.Date;

public class EventActivityPointVo {
    private String id;

    private String eventActivityId;
    
    private Long eventActivityUserinfo;

    private String openid;

    private Long subscribe;

    private Date subscribetime;

    private String nickname;

    private Long sex;

    private String country;

    private String province;

    private String city;

    private String language;

    private String headimgurl;

    private Date createTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventActivityId() {
		return eventActivityId;
	}

	public void setEventActivityId(String eventActivityId) {
		this.eventActivityId = eventActivityId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Long subscribe) {
		this.subscribe = subscribe;
	}

	public Date getSubscribetime() {
		return subscribetime;
	}

	public void setSubscribetime(Date subscribetime) {
		this.subscribetime = subscribetime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getEventActivityUserinfo() {
		return eventActivityUserinfo;
	}

	public void setEventActivityUserinfo(Long eventActivityUserinfo) {
		this.eventActivityUserinfo = eventActivityUserinfo;
	}
    
}