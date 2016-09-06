package com.club.web.module.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.module.domain.repository.CommonTextRepository;
/**
 * 通用文本Do
 * @author zhuzd
 *
 */
@Configurable
public class CommonTextDo {
	
    private Long id;

    private Integer type;

    private String fileUrl;

    private String content;
    
    @Autowired
    private CommonTextRepository repository;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    
    public int saveOrUpdate(){
    	CommonTextDo dbDo = repository.findTextDoById(this.id);
    	if(dbDo == null){
        	return repository.add(this);
    	}
    	return repository.modify(this);
    }
}