package com.club.web.store.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.store.domain.repository.IndentBillRepository;

/**   
* @Title: IndentBillDo.java
* @Package com.club.web.store.domain
* @Description: 结算账单domain类 
* @author hqLin 
* @date 2016/05/06
* @version V1.0   
*/
@Configurable
public class IndentBillDo {
	
	@Autowired
	IndentBillRepository indentBillRepository;
	
    private Long id;

    private Long indentId;

    private Integer status;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndentId() {
        return indentId;
    }

    public void setIndentId(Long indentId) {
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
    
    public int insert() {
    	
    	return indentBillRepository.insert(this);
	}

	public int update() {
		
		return indentBillRepository.update(this);
	}
}