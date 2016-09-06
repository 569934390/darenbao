package com.club.web.store.vo;

import java.util.Date;

/**   
* @Title: IndentBillVo.java
* @Package com.club.web.store.vo
* @Description: 账单VO 
* @author hqLin 
* @date 2016/05/06
* @version V1.0
*/
public class IndentBillVo {
    private String id;

    private String indentId;

    private Integer status;

    private Date createTime;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndentId() {
		return indentId;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}