package com.club.web.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.log.ClubLogManager;
import com.club.web.util.QiNiuUtils;

/** 
 * @author  Youzh
 * @version 1.0 
 * @date create on：2016年3月30日 下午4:58:22
 * @describe   
 * @since 
 * @return  
 */
@Controller
@RequestMapping("/qiNiuController")
public class QiNiuController {
	private static final ClubLogManager logger = ClubLogManager.getLogger(QiNiuController.class);
	
	@Autowired QiNiuUtils qiniu;
	/**
     * 获取七牛云上传token
     */
	@RequestMapping("/getQiNiuUploadToken")
	@ResponseBody
	public Map<String,String> getQiNiuUploadToken() {
		Map<String,String> token= new HashMap();
		token.put("uptoken",qiniu.getQiNiuToken());
		return token;
	}
	
	/**
	 * 获取七牛云上传Filetoken
	 */
	@RequestMapping("/getQiNiuUploadFileToken")
	@ResponseBody
	public Map<String,String> getQiNiuUploadFileToken() {
		Map<String,String> token= new HashMap();
		token.put("uptoken",qiniu.getQiNiuFileToken());
		return token;
	}

}
