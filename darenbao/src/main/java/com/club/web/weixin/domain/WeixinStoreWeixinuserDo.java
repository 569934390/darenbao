package com.club.web.weixin.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.weixin.domain.repository.WeixinStoreWeixinuserRepository;

@Configurable
public class WeixinStoreWeixinuserDo {
	
	@Autowired WeixinStoreWeixinuserRepository weixinStoreWeixinuserRepository;
	
    private Long id;

    private Long storeId;

    private Long weixinuserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getWeixinuserId() {
        return weixinuserId;
    }

    public void setWeixinuserId(Long weixinuserId) {
        this.weixinuserId = weixinuserId;
    }

	public void insert() {
		weixinStoreWeixinuserRepository.insert(this);
	}
 
    
}