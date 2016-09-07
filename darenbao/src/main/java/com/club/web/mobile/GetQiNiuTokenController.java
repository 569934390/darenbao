package com.club.web.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.framework.log.ClubLogManager;
import com.club.web.common.controller.QiNiuController;
import com.club.web.util.QiNiuUtils;

/**
* @Title: WieixnGetQiNiuTokenController.java 
* @Package com.club.web.weixin.controller 
* @Description: TODO(七牛获取Token) 
* @author 柳伟军   
* @date 2016年6月13日 上午10:03:03 
* @version V1.0
 */
@Controller
@RequestMapping("/mobile")
public class GetQiNiuTokenController {

	Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired QiNiuUtils qiniu;
	/**
     * 获取七牛云上传token
     */
	@RequestMapping("/qiniu/getToken")
	@ResponseBody
	public Map<String,Object> getToken(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/json;charset=utf-8");
		Map<String,Object> token= new HashMap();
		try {
			token.put("success", true);
			token.put("uptoken",qiniu.getQiNiuToken());
		} catch (Exception e) {
			token.put("success", false);
			token.put("msg", e.getMessage());
		}
		return token;
	}
}
