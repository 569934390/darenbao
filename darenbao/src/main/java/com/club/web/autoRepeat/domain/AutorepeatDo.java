package com.club.web.autoRepeat.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.autoRepeat.domain.repository.AutoRepeatRepository;

@Configurable
public class AutorepeatDo {
	    @Autowired
	    private AutoRepeatRepository autoRepeatRepository;
	
	    private Long id;

	    private String title;

	    private String keyword;

	    private String content;

	    private Date updateTime;
	    
	    private Integer weigth;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title == null ? null : title.trim();
	    }

	    public String getKeyword() {
	        return keyword;
	    }

	    public void setKeyword(String keyword) {
	        this.keyword = keyword == null ? null : keyword.trim();
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content == null ? null : content.trim();
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }

		public Integer getWeigth() {
			return weigth;
		}

		public void setWeigth(Integer weigth) {
			this.weigth = weigth;
		}
	    
	    
}
