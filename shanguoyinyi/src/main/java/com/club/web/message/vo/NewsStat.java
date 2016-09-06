package com.club.web.message.vo;

import java.util.Date;

/**
 * 消息内容统计类
 * @author zhuzd
 *
 */
public class NewsStat {
    
    private Integer newsCount;

    private Date newsTime;

	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public Date getNewsTime() {
		return newsTime;
	}

	public void setNewsTime(Date newsTime) {
		this.newsTime = newsTime;
	}
}