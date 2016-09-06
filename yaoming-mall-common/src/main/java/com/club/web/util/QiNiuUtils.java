package com.club.web.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.qiniu.util.Auth;

/**
 * @author  Youzh
 * @describe 七牛云工具类
 * @version 1.0 
 * @date  2016年3月28日 上午11:08:19 
 * @return  
 */
@Component
@PropertySource("classpath:/config/qiniu.properties")
public class QiNiuUtils {
	
	@Value("${qiniu.access_key}") private String access_key;
	@Value("${qiniu.secret_key}") private String secret_key;
	@Value("${qiniu.bucketname}") private String bucketname;
	@Value("${qiniu.bucketnamefile}") private String bucketnamefile;
	
	Auth qiniuauth;
	
	public String getQiNiuToken(){
		
		return qiniuauth.uploadToken(this.bucketname, null, 36000000, null);
	}
	public String getQiNiuFileToken(){
		
		return qiniuauth.uploadToken(this.bucketnamefile, null, 36000000, null);
	}
	
	@PostConstruct
	public void init(){
		qiniuauth= Auth.create(access_key, secret_key);
	}
}
